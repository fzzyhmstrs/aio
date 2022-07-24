package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.RotatableFacilityBlockEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.function.ToIntFunction

class CrystallineSwitchBlock(settings: Settings): BlockWithEntity(settings) {

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(LIT, true)?.with(SWITCH_COLOR, SwitchColor.BLUE)?.with(RotatableFacilityBlockEntity.ROTATION,Direction.SOUTH)
    }
    
    @Deprecated("Deprecated in Java")
    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrystallineSwitchBlockEntity(pos, state)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LIT, SWITCH_COLOR, RotatableFacilityBlockEntity.ROTATION)
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return VOXEL_SHAPE
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (world.isClient) return ActionResult.FAIL
        if (hand == Hand.OFF_HAND) return ActionResult.FAIL
        val stack = player.getStackInHand(hand)
        if (stack.isOf(RegisterItem.FACILITY_CONFIGURATOR)){
            return super.onUse(state, world, pos, player, hand, hit)
        }
        val switchEntity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY)
            ?: return ActionResult.FAIL
        if (switchEntity.isUnlocked()) {
            val colorState = state.get(SWITCH_COLOR)
            if (colorState.onUse(state, world, pos, player, hand, hit, switchEntity)) {
                FacilityChimes.SUCCESS.playSound(world, pos)
                return ActionResult.SUCCESS
            }
        }
        FacilityChimes.FAILURE.playSound(world, pos)
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if(!world.isClient) checkType(
            type, RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY
        ) { world2: World, pos: BlockPos, state2: BlockState, blockEntity: CrystallineSwitchBlockEntity ->
            CrystallineSwitchBlockEntity.tick(
                world2,
                pos,
                state2,
                blockEntity
            )
        } else null
    }



    companion object{

        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
        val LIT: BooleanProperty = Properties.LIT
        val SWITCH_COLOR: EnumProperty<SwitchColor> = EnumProperty.of("switch_color",SwitchColor::class.java)

        private val VOXEL_SHAPE = createCuboidShape(4.0, 0.0, 4.0, 12.0, 14.0, 12.0)

        val STATE_TO_LUMINANCE: ToIntFunction<BlockState> = ToIntFunction { state:BlockState -> if(state.get(LIT) != false){15} else {8} }

        fun isSwitchLit(state: BlockState): Boolean?{
            val opt = state.getOrEmpty(LIT)
            return if (opt.isEmpty) {
                null
            } else {
                opt.orElseGet { null }
            }
        }

        fun getDoors(world: World,list: List<BlockPos>): Map<BlockPos, SwitchDoor>{
            val doorList: MutableMap<BlockPos, SwitchDoor> = mutableMapOf()
            list.forEach {
                val chk = world.getBlockState(it).block
                if (chk != null) {
                    if (chk is SwitchDoor) {
                        doorList[it] = chk
                    }
                }
            }
            return doorList
        }

        enum class SwitchColor(private val str: String): StringIdentifiable {

            BLUE("blue") {
                //general door, gate, etc. use
                override fun onUse(
                    state: BlockState,
                    world: World,
                    pos: BlockPos,
                    player: PlayerEntity,
                    hand: Hand,
                    hit: BlockHitResult,
                    entity: CrystallineSwitchBlockEntity
                ): Boolean {
                    val doors = getDoors(world, entity.getDoors())
                    if (doors.isEmpty()) return false
                    doors.forEach {
                        if (it.value.doorType() == SwitchDoor.DoorType.DOOR){
                            it.value.openDoor(world, player, it.key, world.getBlockState(it.key))
                        }
                    }
                    return true
                }

                override fun colorMessage(): Text {
                    return TranslatableText("message.switch.blue")
                }
            },
            RED("red") {
                // locked down?
                override fun onUse(
                    state: BlockState,
                    world: World,
                    pos: BlockPos,
                    player: PlayerEntity,
                    hand: Hand,
                    hit: BlockHitResult,
                    entity: CrystallineSwitchBlockEntity
                ): Boolean {
                    return false
                }

                override fun colorMessage(): Text {
                    return TranslatableText("message.switch.red")
                }
            },
            GREEN("green") {
                // portal use
                override fun onUse(
                    state: BlockState,
                    world: World,
                    pos: BlockPos,
                    player: PlayerEntity,
                    hand: Hand,
                    hit: BlockHitResult,
                    entity: CrystallineSwitchBlockEntity
                ): Boolean {
                    val doors = getDoors(world, entity.getDoors())
                    if (doors.isEmpty()) return false
                    doors.forEach {
                        if (it.value.doorType() == SwitchDoor.DoorType.PORTAL){
                            it.value.openDoor(world, player, it.key, state)
                        }
                    }
                    return true
                }

                override fun colorMessage(): Text {
                    return TranslatableText("message.switch.green")
                }
            },
            YELLOW("yellow") {
                //teleporter use
                override fun onUse(
                    state: BlockState,
                    world: World,
                    pos: BlockPos,
                    player: PlayerEntity,
                    hand: Hand,
                    hit: BlockHitResult,
                    entity: CrystallineSwitchBlockEntity
                ): Boolean {
                    val doors = getDoors(world, entity.getDoors())
                    if (doors.isEmpty()) return false
                    doors.forEach {
                        if (it.value.doorType() == SwitchDoor.DoorType.TELEPORTER){
                            it.value.openDoor(world, player, it.key, state)
                        }
                    }
                    return true
                }

                override fun colorMessage(): Text {
                    return TranslatableText("message.switch.yellow")
                }
            };

            abstract fun onUse(
                state: BlockState,
                world: World,
                pos: BlockPos,
                player: PlayerEntity,
                hand: Hand,
                hit: BlockHitResult,
                entity: CrystallineSwitchBlockEntity): Boolean

            abstract fun colorMessage(): Text

            override fun asString(): String {
                return this.str
            }
        }
    }

}

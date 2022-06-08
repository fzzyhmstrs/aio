package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.function.ToIntFunction

class CrystallineSwitchBlock(settings: Settings): BlockWithEntity(settings) {

    init{
        defaultState = stateManager.defaultState.with(LIT, true).with(SWITCH_COLOR, SwitchColor.BLUE)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrystallineSwitchBlockEntity(pos, state)
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

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LIT, SWITCH_COLOR)
    }

    companion object{

        val LIT = Properties.LIT
        private val SWITCH_COLOR = EnumProperty.of("switch_color",SwitchColor::class.java)

        val STATE_TO_LUMINANCE: ToIntFunction<BlockState> = ToIntFunction { state:BlockState -> if(state.get(LIT) != false){15} else {8} }

        fun isSwitchLit(state: BlockState): Boolean?{
            val opt = state.getOrEmpty(LIT)
            return if (opt.isEmpty) {
                null
            } else {
                opt.orElseGet { null }
            }
        }

        enum class SwitchColor: StringIdentifiable {

            BLUE,
            GREEN,
            RED,
            PINK,
            YELLOW;

            override fun asString(): String {
                return this.name
            }
        }

    }

}
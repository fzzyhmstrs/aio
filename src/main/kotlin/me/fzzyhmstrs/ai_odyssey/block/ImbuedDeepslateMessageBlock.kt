package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.ConfiguratorInteractive
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import me.fzzyhmstrs.ai_odyssey.screen.ImbuedMessageScreenHandler
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.IndexedEnum
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.enumToIndexes
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityPose
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ImbuedDeepslateMessageBlock(settings: Settings): Block(settings), ConfiguratorInteractive {

    companion object{
        val MESSAGE: EnumProperty<Message> = EnumProperty.of("message",Message::class.java)
        private val MESSAGE_VISIBLE = BooleanProperty.of("message_visible")
        val messageMap: Map<Int, Message> by lazy { enumToIndexes(Message.values()) }

        enum class Message(private val str: String, val x: Int, val y: Int): StringIdentifiable, IndexedEnum{

            GET_OUT_1("get_out_1",1,1),
            GET_OUT_2("get_out_2",2,1),
            GET_OUT_3("get_out_3",3,1),
            RUN_1("run_1",4,1),
            RUN_2("run_2",5,1),
            WATCHES_1("watches_1",1,2),
            WATCHES_2("watches_2",2,2),
            WATCHES_3("watches_3",3,2),
            CRYSTALS_1("crystals_1",1,4),
            CRYSTALS_2("crystals_2",2,4),
            CRYSTALS_3("crystals_3",3,4),
            CRYSTALS_4("crystals_4",4,4),
            DEVOURS_1("devours_1",1,3),
            DEVOURS_2("devours_2",2,3),
            DEVOURS_3("devours_3",3,3),
            HUNT_YOU_1("hunt_you_1",5,4),
            HUNT_YOU_2("hunt_you_2",1,5),
            HUNT_YOU_3("hunt_you_3",2,5),
            HUNT_YOU_4("hunt_you_4",3,5),
            HUNT_YOU_5("hunt_you_5",4,5),
            HUNT_YOU_6("hunt_you_6",5,5),
            LEAVE_1("leave_1",4,2),
            LEAVE_2("leave_2",5,2),
            ESCAPE_1("escape_1",4,3),
            ESCAPE_2("escape_2",5,3),
            NONE("none",0,0);

            override fun asString(): String {
                return this.str
            }

            override fun coordinatesToIndex(): Int{
                return this.x + 10 * this.y
            }

        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(MESSAGE,Message.NONE)?.with(MESSAGE_VISIBLE,false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(MESSAGE, MESSAGE_VISIBLE)
    }

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (player.isCreative && player.pose == EntityPose.CROUCHING){
            world.setBlockState(pos, state.cycle(MESSAGE))
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    @Deprecated("Deprecated in Java")
    override fun calcBlockBreakingDelta(
        state: BlockState,
        player: PlayerEntity,
        world: BlockView,
        pos: BlockPos
    ): Float {
        val f = state.getHardness(world, pos)
        if (f == -1.0f) {
            val level = EnchantmentHelper.getLevel(RegisterEnchantment.IMBUED_TOUCH,player.mainHandStack)
            return if (level > 0){
                val i = if (player.canHarvest(state)) 30 else 100
                player.getBlockBreakingSpeed(state) / f / i.toFloat()
            } else {
                0.0f
            }
        }
        val i = if (player.canHarvest(state)) 30 else 100
        return player.getBlockBreakingSpeed(state) / f / i.toFloat()
    }

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        return if (user != null) {
            user.openHandledScreen(state.createScreenHandlerFactory(world, pos))
            ActionResult.CONSUME
        } else {
            ActionResult.FAIL        
        }
    }

    @Deprecated("Deprecated in Java")
    override fun createScreenHandlerFactory(
        state: BlockState,
        world: World,
        pos: BlockPos
    ): NamedScreenHandlerFactory {
        val text = this.name
        return SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, _: PlayerEntity ->
            ImbuedMessageScreenHandler(
                syncId,
                inventory,
                ScreenHandlerContext.create(world, pos)
            )
        }, text)
    }
    
}

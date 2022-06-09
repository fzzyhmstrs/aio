package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityPose
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ImbuedDeepslateMessageBlock(settings: Settings): Block(settings) {

    companion object{
        private val MESSAGE = EnumProperty.of("message",Message::class.java)
        private val MESSAGE_VISIBLE = BooleanProperty.of("message_visible")

        enum class Message: StringIdentifiable{

            GET_OUT_1,
            GET_OUT_2,
            GET_OUT_3,
            RUN_1,
            RUN_2,
            WATCHES_1,
            WATCHES_2,
            WATCHES_3,
            CRYSTALS_1,
            CRYSTALS_2,
            CRYSTALS_3,
            CRYSTALS_4,
            DEVOURS_1,
            DEVOURS_2,
            DEVOURS_3,
            HUNT_YOU_1,
            HUNT_YOU_2,
            HUNT_YOU_3,
            HUNT_YOU_4,
            HUNT_YOU_5,
            HUNT_YOU_6,
            LEAVE_1,
            LEAVE_2,
            ESCAPE_1,
            ESCAPE_2,
            NONE;

            override fun asString(): String {
                return this.name
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


}
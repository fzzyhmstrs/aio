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
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ImbuedDeepslateSplatterBlock(settings: Settings): Block(settings) {

    companion object{
        private val SPLATTER = EnumProperty.of("splatter",Message::class.java)
        private val SPLATTER_VISIBLE = BooleanProperty.of("splatter_visible")

        enum class Message: StringIdentifiable{

            EYE_1,
            EYE_2,
            TALLY_1,
            TALLY_2,
            TALLY_3,
            HAND_PRINT_1,
            HAND_PRINT_2,
            HAND_SMEAR_1,
            HAND_SMEAR_2,
            HAND_SMEAR_3,
            HAND_SMEAR_4,
            BLOOD_1,
            BLOOD_2,
            BLOOD_3,
            BLOOD_4,
            BLOOD_5,
            PICTOGRAPH_1,
            PICTOGRAPH_2,
            PICTOGRAPH_3,
            PICTOGRAPH_4,
            ARROW_1,
            ARROW_2,
            ARROW_3,
            ARROW_4,
            X_1,
            NONE;

            override fun asString(): String {
                return this.name
            }
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(SPLATTER,Message.NONE)?.with(SPLATTER_VISIBLE,false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(SPLATTER, SPLATTER_VISIBLE)
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
            world.setBlockState(pos, state.cycle(SPLATTER))
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
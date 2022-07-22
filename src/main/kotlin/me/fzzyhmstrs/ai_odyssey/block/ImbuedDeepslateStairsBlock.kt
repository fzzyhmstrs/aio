package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import net.minecraft.block.BlockState
import net.minecraft.block.StairsBlock
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class ImbuedDeepslateStairsBlock(baseBlockState: BlockState, settings: Settings, val fallbackHardness: Float):StairsBlock(baseBlockState,settings) {

    @Deprecated("Deprecated in Java")
    override fun calcBlockBreakingDelta(
        state: BlockState,
        player: PlayerEntity,
        world: BlockView,
        pos: BlockPos
    ): Float {
        val f = state.getHardness(world, pos)
        if (f == -1.0f) {
            return 0.0f
        }
        val level = EnchantmentHelper.getLevel(RegisterEnchantment.IMBUED_TOUCH,player.mainHandStack)
        val i = if (player.canHarvest(state)) 30 else 100
        return if (level > 0){
            player.getBlockBreakingSpeed(state) / fallbackHardness / i.toFloat()
        } else {
            player.getBlockBreakingSpeed(state) / f / i.toFloat()
        }
    }

}
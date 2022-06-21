package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties

class PetroglyphCrackedBlock(settings: Settings): Block(settings) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING, HALF)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(FACING,ctx.playerFacing.opposite)
    }

    companion object {
        private val FACING = HorizontalFacingBlock.FACING
        private val HALF = Properties.BLOCK_HALF
    }

}

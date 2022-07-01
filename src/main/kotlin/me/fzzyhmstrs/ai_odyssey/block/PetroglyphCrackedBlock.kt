package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.enums.BlockHalf
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

class PetroglyphCrackedBlock(settings: Settings): Block(settings) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING, HALF)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val direction = ctx.side
        val blockPos = ctx.blockPos
        val initialState = super.getPlacementState(ctx)?.with(FACING,ctx.playerFacing.opposite)
        if (direction == Direction.DOWN || direction != Direction.UP && ctx.hitPos.y - blockPos.y.toDouble() > 0.5) {
            return initialState?.with(HALF,BlockHalf.TOP)
        }
        return initialState?.with(HALF, BlockHalf.BOTTOM)
    }

    companion object {
        private val FACING = HorizontalFacingBlock.FACING
        private val HALF = Properties.BLOCK_HALF
    }

}

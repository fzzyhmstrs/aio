package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import java.util.*

class BullKelpBlock(settings: Settings): AbstractPlantStemBlock(settings, Direction.UP, VoxelShapes.fullCube(),true,0.10) {

    override fun getPlant(): Block {
        return RegisterBlock.BULL_KELP_PLANT
    }

    private fun getStreamer(): BullKelpStreamerBlock{
        return RegisterBlock.BULL_KELP_STREAMER
    }

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        var blockPos = pos.offset(growthDirection)
        var i = (state.get(AGE) + 1).coerceAtMost(25)
        val j = getGrowthLength(random)
        var k = 0
        while (k < j && chooseStemState(world.getBlockState(blockPos))) {
            world.setBlockState(blockPos, state.with(AGE, i) as BlockState)
            getStreamer().placeStreamer(world, blockPos)
            getStreamer().removeStreamer(world, pos)
            blockPos = blockPos.offset(growthDirection)
            i = (i + 1).coerceAtMost(25)
            ++k
        }

    }

    override fun getGrowthLength(random: Random): Int {
        return 1
    }

    override fun chooseStemState(state: BlockState): Boolean {
        return state.isOf(Blocks.WATER)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        return if (fluidState.isIn(FluidTags.WATER) && fluidState.level == 8) {
            super.getPlacementState(ctx)
        } else null
    }

    override fun canAttachTo(state: BlockState): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState?): FluidState? {
        return Fluids.WATER.getStill(false)
    }

}
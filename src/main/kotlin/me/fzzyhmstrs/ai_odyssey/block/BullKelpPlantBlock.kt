package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.block.BullKelpBlock.Companion.BABY
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class BullKelpPlantBlock(settings: Settings):
    AbstractPlantBlock(settings,Direction.UP, createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0),true)
    , FluidFillable {

    override fun getStem(): AbstractPlantStemBlock {
        return RegisterBlock.BULL_KELP
    }

    override fun getPlant(): Block {
        return this
    }

    private fun getStreamer(): BullKelpStreamerBlock{
        return RegisterBlock.BULL_KELP_STREAMER
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)
        if (!world.isClient) {
            val pos1 = pos.offset(growthDirection.opposite)
            getStreamer().placeStreamer(world, pos1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val tempState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
        if (tempState.isOf(stem)){
            return tempState.with(BABY, true)
        }
        return tempState
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getStill(false)
    }

    override fun canFillWithFluid(world: BlockView, pos: BlockPos, state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun tryFillWithFluid(
        world: WorldAccess,
        pos: BlockPos,
        state: BlockState,
        fluidState: FluidState
    ): Boolean {
        return false
    }
}
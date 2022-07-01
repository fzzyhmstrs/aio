package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Waterloggable
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

class PrismaticCrystalBlock(settings: Settings): Block(settings), Waterloggable {

    companion object{
        private val UP = Properties.UP
        private val FACING = Properties.FACING
        private val WATERLOGGED = Properties.WATERLOGGED
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(UP, FACING, WATERLOGGED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val initialState = super.getPlacementState(ctx)
        val world = ctx.world
        val pos = ctx.blockPos
        val facing = ctx.side
        val chk = world.getBlockState(pos.offset(facing)).block
        val placementState = if(chk is PrismaticCrystalBlock){
            initialState?.with(UP, true)?.with(FACING, facing)
        } else {
            initialState?.with(UP, false)?.with(FACING, facing)
        }


        return placementState
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        val facing = state.get(FACING)
        if (facing == direction){
            return if (neighborState.block is PrismaticCrystalBlock){
                state.with(UP,true)
            } else {
                state.with(UP,false)
            }
        } else if (facing == direction.opposite){
            if (neighborState.block !is PrismaticCrystalBlock){
                return state.with(FACING,facing.opposite).with(UP, false)
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun getFluidState(state: BlockState): FluidState {
        return if (state.get(WATERLOGGED)) {
            Fluids.WATER.getStill(false)
        } else super.getFluidState(state)
    }


}
package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.*
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

class PrismaticCrystalBlock(settings: Settings): Block(settings), Waterloggable {

    companion object{
        private val FULL = Properties.UP
        private val FACING = Properties.FACING
        private val WATERLOGGED = Properties.WATERLOGGED
        private val UP_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        private val DOWN_SHAPE = createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0)
        private val SOUTH_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0)
        private val NORTH_SHAPE = createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)
        private val EAST_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0)
        private val WEST_SHAPE = createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        val full = state.get(FULL)
        if (full) return VoxelShapes.fullCube()
        return when(state.get(FACING)){
            Direction.UP->{UP_SHAPE}
            Direction.DOWN->{DOWN_SHAPE}
            Direction.NORTH->{NORTH_SHAPE}
            Direction.SOUTH->{SOUTH_SHAPE}
            Direction.WEST->{WEST_SHAPE}
            Direction.EAST->{EAST_SHAPE}
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FULL, FACING, WATERLOGGED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val initialState = super.getPlacementState(ctx)
        val world = ctx.world
        val pos = ctx.blockPos
        val facing = ctx.side
        val chk = world.getBlockState(pos.offset(facing)).isSideSolidFullSquare(world,pos.offset(facing),facing.opposite)
        val chk2 = world.getBlockState(pos).fluidState.isIn(FluidTags.WATER)
        val placementState = if(chk){
            initialState?.with(FULL, true)?.with(FACING, facing)?.with(WATERLOGGED,chk2)
        } else {
            initialState?.with(FULL, false)?.with(FACING, facing)?.with(WATERLOGGED,chk2)
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
            return if (!(neighborState.block is FluidBlock || neighborState.isAir) && neighborState.isSideSolidFullSquare(world, pos, direction.opposite)){
                state.with(FULL,true)
            } else {
                state.with(FULL,false)
            }
        } else if (facing == direction.opposite){
            if ((neighborState.block is FluidBlock || neighborState.isAir) && state.get(FULL) == true){
                return state.with(FACING,facing.opposite).with(FULL, false)
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

    private fun stateIsFluid(state: BlockState): Boolean{
        return !state.fluidState.isEmpty
    }


}
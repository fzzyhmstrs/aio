package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidFillable
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

abstract class AbstractGargantuanKelpStreamerBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        protected val FACING = Properties.HORIZONTAL_FACING
        protected val AGE = Properties.AGE_1
        protected val LENGTH = IntProperty.of("length",0,1)
    }

    abstract fun getStreamer(): AbstractGargantuanKelpStreamerBlock

    abstract fun getStem(): AbstractGargantuanKelpBlock

    abstract fun getPlant(): AbstractGargantuanKelpPlantBlock

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING, AGE, LENGTH)
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
        val streamerDir = state.get(FACING)
        if (direction == streamerDir.opposite && !canAttachTo(neighborState) || !state.canPlaceAt(world, pos)){
            world.createAndScheduleBlockTick(pos, this, 1)
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    open fun placeStreamer(world: World, blockPos: BlockPos){
        val rnd = world.random.nextInt(2)
        val length = 1 - rnd
        var offset = 1
        for (i in rnd..1){
            for (dir in Direction.Type.HORIZONTAL){
                val newPos = blockPos.offset(dir,offset)
                if (canPlaceIn(world.getBlockState(newPos))) {
                    world.setBlockState(
                        newPos,
                        getStreamer().defaultState.with(AGE, i).with(LENGTH, length).with(FACING, dir)
                    )
                }
            }
            offset++
        }
    }

    open fun removeStreamer(world: World, blockPos: BlockPos){
        for (dir in Direction.Type.HORIZONTAL){
            val newBlockPos = blockPos.offset(dir)
            if (world.getBlockState(newBlockPos).block is AbstractGargantuanKelpStreamerBlock){
                world.removeBlock(newBlockPos,false)
            }
        }
    }

    protected open fun canPlaceIn(state: BlockState): Boolean{
        return state.isOf(Blocks.WATER)
    }

    open fun canAttachTo(state: BlockState): Boolean{
        return state.isOf(getStem()) || state.isOf(getPlant()) || state.isOf(getStreamer())
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val streamerDir = state.get(FACING)
        if (!canAttachTo(world.getBlockState(pos.offset(streamerDir.opposite)))) {
            world.removeBlock(pos, false)
        }
    }

    override fun canFillWithFluid(world: BlockView?, pos: BlockPos?, state: BlockState?, fluid: Fluid?): Boolean {
        return false
    }

    override fun tryFillWithFluid(
        world: WorldAccess?,
        pos: BlockPos?,
        state: BlockState?,
        fluidState: FluidState?
    ): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getStill(false)
    }
}
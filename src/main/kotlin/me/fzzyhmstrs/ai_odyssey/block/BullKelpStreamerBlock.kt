package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.block.BullKelpBlock.Companion.BABY
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidFillable
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*


class BullKelpStreamerBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        private val age = Properties.AGE_2
        private val growthDirection = Direction.SOUTH
    }

    private fun getBulb(): Block{
        return RegisterBlock.BULL_KELP
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(age, BABY)
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.offset(growthDirection.opposite)
        val blockState = world.getBlockState(blockPos)
        return  blockState.isOf(this.getBulb()) || blockState.isOf(this)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)",
        "net.minecraft.block.Block"
    )
    )
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        if (direction == growthDirection.opposite && !state.canPlaceAt(world, pos)){
            world.createAndScheduleBlockTick(pos, this, 1)
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
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

    private fun canPlaceIn(state: BlockState): Boolean{
        return state.isOf(Blocks.WATER) || state.isOf(this)
    }

    fun placeStreamer(world: World, blockPos: BlockPos, baby: Boolean = false){
        if (world.isClient) return
        var newBlockPos = blockPos
        if (!baby) {
            for (i in 0..2) {
                newBlockPos = newBlockPos.offset(growthDirection)
                if (canPlaceIn(world.getBlockState(newBlockPos))) {
                    world.setBlockState(newBlockPos, RegisterBlock.BULL_KELP_STREAMER.defaultState.with(age, i).with(BABY, false))
                } else {
                    break
                }
            }
        } else {
            newBlockPos = newBlockPos.offset(growthDirection)
            if (canPlaceIn(world.getBlockState(newBlockPos))) {
                world.setBlockState(newBlockPos, RegisterBlock.BULL_KELP_STREAMER.defaultState.with(age, 0).with(BABY,true))
            }
        }
    }

    fun removeStreamer(world: World, blockPos: BlockPos){
        if (world.isClient) return
        var newBlockPos = blockPos
        for (i in 0..2){
            newBlockPos = newBlockPos.offset(growthDirection)
            if (world.getBlockState(newBlockPos).isOf(this)){
                world.removeBlock(newBlockPos,false)
            } else if (world.getBlockState(newBlockPos).isOf(RegisterBlock.BULL_KELP) || world.getBlockState(newBlockPos).isOf(RegisterBlock.BULL_KELP_PLANT)){
                break //break the replacement loop if it might delete a streamer from a downstream bull kelp
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false)
        }
    }
}
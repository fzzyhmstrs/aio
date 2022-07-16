package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.*
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.*

abstract class AbstractGargantuanKelpBlock(settings: Settings, private val growthChance: Double = 0.06):
    AbstractPlantStemBlock(settings, Direction.UP, VoxelShapes.fullCube(),true,growthChance),
    FluidFillable {

    companion object{
        private val AGE = IntProperty.of("age", 0, 150)
    }

    abstract fun getStreamer(): AbstractGargantuanKelpStreamerBlock

    abstract fun getWood(): PillarBlock

    override fun appendProperties(builder: StateManager.Builder<Block?, BlockState?>) {
        builder.add(AbstractPlantStemBlock.AGE)
    }

    override fun getRandomGrowthState(world: WorldAccess): BlockState {
        return defaultState.with(AGE, world.random.nextInt(150)) as BlockState
    }

    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val blockPos = pos.offset(growthDirection)
        if (state.get(AGE) < 150 &&
            random.nextDouble() < this.growthChance &&
            chooseStemState(world.getBlockState(blockPos)))
        {
            world.setBlockState(blockPos, age(state, world.random))
            getStreamer().placeStreamer(world, pos)
        }
    }

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        var blockPos = pos.offset(growthDirection)
        var i = (state.get(AGE) + 1).coerceAtMost(150)
        val j = getGrowthLength(random)
        var k = 0
        while (k < j && chooseStemState(world.getBlockState(blockPos))) {
            world.setBlockState(blockPos, state.with(AGE, i) as BlockState)
            getStreamer().placeStreamer(world, blockPos.offset(growthDirection.opposite))
            blockPos = blockPos.offset(growthDirection)
            i = (i + 1).coerceAtMost(150)
            ++k
        }
    }

    override fun withMaxAge(state: BlockState): BlockState {
        return state.with(AGE,150)
    }

    override fun hasMaxAge(state: BlockState): Boolean {
        return true
    }

    override fun getGrowthLength(random: Random): Int {
        return random.nextInt(3) + 1
    }

    override fun chooseStemState(state: BlockState): Boolean {
        return state.isOf(Blocks.WATER)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        return if (fluidState.isIn(FluidTags.WATER) && fluidState.level == 8) {
            super.getPlacementState(ctx)
        } else {
            return getWood().defaultState.with(PillarBlock.AXIS, ctx.side.axis) as BlockState
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
    ): BlockState? {
        if (direction == growthDirection.opposite && !canAttachTo(neighborState)) {
            world.createAndScheduleBlockTick(pos, this, 1)
        }
        if (direction == growthDirection && (neighborState.isOf(this) || neighborState.isOf(this.plant))) {
            return copyState(state, this.plant.defaultState)
        }
        if (tickWater) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val newPos = pos.offset(growthDirection.opposite)
        if (!canAttachTo(world.getBlockState(newPos))) {
            world.breakBlock(pos, true)
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState?): FluidState? {
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

    override fun canAttachTo(state: BlockState): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
    }

}
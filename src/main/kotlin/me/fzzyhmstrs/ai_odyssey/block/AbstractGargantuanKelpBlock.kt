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
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

abstract class AbstractGargantuanKelpBlock(settings: Settings,
                                           protected val growthDirection: Direction,
                                           protected val outlineShape: VoxelShape,
                                           private val growthChance: Double = 0.25,
                                           private val tickWater: Boolean = true):
    Block(settings),
    FluidFillable, Fertilizable {

    companion object{
        private val AGE = IntProperty.of("age", 0, 150)
    }

    init{
        defaultState = (stateManager.defaultState as BlockState).with(AGE, 0) as BlockState
    }

    abstract fun getStreamer(): AbstractGargantuanKelpStreamerBlock

    abstract fun getWood(): PillarBlock

    abstract fun getPlant(): AbstractGargantuanKelpPlantBlock

    open fun getStem(): AbstractGargantuanKelpBlock{
        return this
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape? {
        return this.outlineShape
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.offset(growthDirection.opposite)
        val blockState = world.getBlockState(blockPos)
        return if (!canAttachTo(blockState)) {
            false
        } else blockState.isOf(getStem()) || blockState.isOf(getPlant()) || blockState.isSideSolidFullSquare(
            world, blockPos,
            growthDirection
        )
    }

    override fun appendProperties(builder: StateManager.Builder<Block?, BlockState?>) {
        builder.add(AGE)
    }

    fun getRandomGrowthState(world: WorldAccess): BlockState {
        return defaultState.with(AGE, world.random.nextInt(150)) as BlockState
    }

    override fun hasRandomTicks(state: BlockState): Boolean {
        return state.get(AGE) < 150
    }

    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val blockPos = pos.offset(growthDirection)
        if (state.get(AGE) < 150 &&
            random.nextDouble() < this.growthChance &&
            chooseStemState(world.getBlockState(blockPos)))
        {
            world.setBlockState(blockPos, age(state))
            getStreamer().placeStreamer(world, pos)
        }
    }

    protected open fun age(state: BlockState): BlockState {
        return state.cycle(AGE)
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

    protected fun getGrowthLength(random: Random): Int {
        return random.nextInt(3) + 1
    }

    fun chooseStemState(state: BlockState): Boolean {
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
        if (direction == growthDirection && (neighborState.isOf(this) || neighborState.isOf(getPlant()))) {
            return copyState(state, getPlant().defaultState)
        }
        if (tickWater) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    private fun copyState(from: BlockState?, to: BlockState?): BlockState? {
        return to
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val newPos = pos.offset(growthDirection.opposite)
        if (!canAttachTo(world.getBlockState(newPos))) {
            world.breakBlock(pos, true)
        }
    }

    override fun isFertilizable(world: BlockView, pos: BlockPos, state: BlockState?, isClient: Boolean): Boolean {
        return chooseStemState(world.getBlockState(pos.offset(growthDirection)))
    }

    override fun canGrow(world: World?, random: Random?, pos: BlockPos?, state: BlockState?): Boolean {
        return true
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

    protected fun canAttachTo(state: BlockState): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
    }

}
package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.*
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.*
import java.util.*

abstract class AbstractGargantuanKelpPlantBlock(settings: Settings, protected val growthDirection: Direction, protected val outlineShape: VoxelShape, private val tickWater: Boolean = false):
    Block(settings)
    , FluidFillable, Fertilizable {

    private fun copyState(from: BlockState, to: BlockState): BlockState {
        return to
    }

    abstract fun getWood(): PillarBlock

    open fun getPlant(): AbstractGargantuanKelpPlantBlock{
        return this
    }

    abstract fun getStem(): AbstractGargantuanKelpBlock

    abstract fun getStreamer(): AbstractGargantuanKelpStreamerBlock

    override fun getPickStack(world: BlockView, pos: BlockPos, state: BlockState): ItemStack {
        return ItemStack(getWood())
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val blockState = ctx.world.getBlockState(ctx.blockPos.offset(growthDirection))
        return if (blockState.isOf(getStem()) || blockState.isOf(getPlant())) {
            getPlant().defaultState
        } else this.getRandomGrowthState(ctx.world)
    }

    open fun getRandomGrowthState(world: WorldAccess): BlockState {
        return defaultState
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
        if (direction == growthDirection.opposite && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1)
        }
        val gargantuanKelpBlock: AbstractGargantuanKelpBlock = getStem()
        if (direction == growthDirection && !neighborState.isOf(this) && !neighborState.isOf(gargantuanKelpBlock)) {
            return copyState(state, gargantuanKelpBlock.getRandomGrowthState(world))
        }
        if (tickWater) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos?, random: Random?) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.offset(growthDirection.opposite)
        val blockState = world.getBlockState(blockPos)
        return if (!this.canAttachTo(blockState)) {
            false
        } else blockState.isOf(getStem()) || blockState.isOf(getPlant()) || blockState.isSideSolidFullSquare(
            world, blockPos,
            growthDirection
        )
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape? {
        return outlineShape
    }

    protected open fun canAttachTo(state: BlockState?): Boolean {
        return true
    }

    override fun isFertilizable(world: BlockView, pos: BlockPos, state: BlockState, isClient: Boolean): Boolean {
        val optional = getStemHeadPos(world, pos, state.block)
        return optional.isPresent && getStem().chooseStemState(
            world.getBlockState(
                optional.get().offset(growthDirection)
            )
        )
    }

    override fun canGrow(world: World, random: Random, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        val optional = getStemHeadPos(world, pos, state.block)
        if (optional.isPresent) {
            val blockState = world.getBlockState(optional.get())
            (blockState.block as AbstractPlantStemBlock).grow(world, random, optional.get(), blockState)
        }
    }

    private fun getStemHeadPos(world: BlockView, pos: BlockPos, block: Block): Optional<BlockPos> {
        return BlockLocating.findColumnEnd(world, pos, block, growthDirection, getStem())
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState?): FluidState? {
        return Fluids.WATER.getStill(false)
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

    @Deprecated("Deprecated in Java")
    override fun canReplace(state: BlockState?, context: ItemPlacementContext): Boolean {
        val bl = super.canReplace(state, context)
        return if (bl && context.stack.isOf(getStem().asItem())) {
            false
        } else bl
    }
}
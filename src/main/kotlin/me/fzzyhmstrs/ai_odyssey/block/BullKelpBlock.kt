package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class BullKelpBlock(settings: Settings, private val growthChance: Double = 0.10):
    AbstractPlantStemBlock(settings, Direction.UP, VoxelShapes.fullCube(),true,growthChance),
    FluidFillable {

    companion object{
        internal val BABY = BooleanProperty.of("bull_kelp_baby")
    }

    private fun isBaby(state: BlockState): Boolean{
        return state.get(BABY)
    }

    override fun getPlant(): Block {
        return RegisterBlock.BULL_KELP_PLANT
    }

    private fun getStreamer(): BullKelpStreamerBlock{
        return RegisterBlock.BULL_KELP_STREAMER
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(BABY)
    }

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        var blockPos = pos.offset(growthDirection)
        var i = (state.get(AGE) + 1).coerceAtMost(25)
        val j = getGrowthLength(random)
        var k = 0
        while (k < j && chooseStemState(world.getBlockState(blockPos))) {
            if (isBaby(state)){
                blockPos = blockPos.offset(growthDirection.opposite)
                world.setBlockState(blockPos, state.with(AGE, (i-1).coerceAtLeast(0)).with(BABY,false) as BlockState)
            } else {
                world.setBlockState(blockPos, state.with(AGE, i) as BlockState)
            }
            getStreamer().removeStreamer(world, pos)
            getStreamer().placeStreamer(world, blockPos, false)
            blockPos = blockPos.offset(growthDirection)
            i = (i + 1).coerceAtMost(25)
            ++k
        }

    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        if (world is ServerWorld) {
            getStreamer().placeStreamer(world, pos,isBaby(state))
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)
        if (!world.isClient) {
            val pos1 = pos.offset(growthDirection.opposite)
            getStreamer().placeStreamer(world, pos1, true)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (state.get(AGE) < 25 &&
            random.nextDouble() < growthChance &&
            chooseStemState(world.getBlockState(pos.offset(growthDirection))))
        {
            val pos1: BlockPos = if (isBaby(state)){
                world.setBlockState(pos, state.with(BABY,false))
                pos
            } else {
                world.setBlockState(pos.offset(growthDirection), age(state, world.random))
                pos.offset(growthDirection)
            }
            getStreamer().placeStreamer(world, pos1, false)
            getStreamer().removeStreamer(world, pos)
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
            val downState = ctx.world.getBlockState(ctx.blockPos)
            val baby = !downState.isOf(this)
            super.getPlacementState(ctx)?.with(BABY,baby)
        } else null
    }

    override fun canAttachTo(state: BlockState): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
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

}
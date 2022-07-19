package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.TropicalFishEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import kotlin.math.abs

class SeaAnemoneBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{

        private val FACING = Properties.FACING
        private val VOXEL_SHAPE = createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING)
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return VOXEL_SHAPE
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        val blockState = ctx.world.getBlockState(ctx.blockPos)
        return if (fluidState.isIn(FluidTags.WATER) &&
            fluidState.level == 8 &&
            blockState.isSideSolidFullSquare(ctx.world,ctx.blockPos.offset(ctx.side.opposite),ctx.side))
        {
            super.getPlacementState(ctx)?.with(FACING,ctx.side)
        } else null
    }


    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.85, 0.85, 0.85))
        if (!(world.isClient || entity.lastRenderX == entity.getX() && entity.lastRenderY == entity.getY() && entity.lastRenderZ == entity.getZ())) {
            val d = abs(entity.getX() - entity.lastRenderX)
            val e = abs(entity.getY() - entity.lastRenderY)
            val f = abs(entity.getZ() - entity.lastRenderZ)
            if (d >= 0.003 || e >= 0.003 || f >= 0.003) {
                if (!entity.isDead) {
                    if (!(entity is TropicalFishEntity && (entity.variant == 65536 || entity.variant == 917504))) {
                        entity.damage(DamageSource.GENERIC, 0.5f)
                        val rnd = world.random.nextFloat()
                        if (rnd < 0.1F){
                            EffectQueue.addStatusToQueue(entity,StatusEffects.POISON,100,0)
                        }
                    }
                }
            }
        }
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

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getStill(false)
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
    }
}
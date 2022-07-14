package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.ai_odyssey.registry.RegisterTag
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.FluidTags
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

class SeaAppleBushBlock(settings: Settings): SweetBerryBushBlock(settings), FluidFillable {


    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        return if (fluidState.isIn(FluidTags.WATER) && fluidState.level == 8) {
            super.getPlacementState(ctx)
        } else null
    }


    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.92, 0.92, 0.92))
    }

    override fun getPickStack(world: BlockView, pos: BlockPos, state: BlockState): ItemStack {
        return ItemStack(Items.APPLE)
    }

    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val i = state.get(AGE)
        if (i < 3 && random.nextInt(6) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, i + 1) as BlockState, NOTIFY_LISTENERS)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult? {
        val bl: Boolean
        val i = state.get(AGE)
        bl = i == 3
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS
        }
        if (bl) {
            dropStack(world, pos, ItemStack(RegisterItem.SEA_APPLE, 1))
            world.playSound(
                null,
                pos,
                SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,
                SoundCategory.BLOCKS,
                1.0f,
                0.8f + world.random.nextFloat() * 0.4f
            )
            world.setBlockState(pos, state.with(AGE, 1) as BlockState, NOTIFY_LISTENERS)
            return ActionResult.success(world.isClient)
        }
        return ActionResult.PASS
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
    override fun getFluidState(state: BlockState?): FluidState? {
        return Fluids.WATER.getStill(false)
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val downState = world.getBlockState(pos.down())
        return  downState.isSideSolidFullSquare(world, pos,Direction.UP) && (downState.material.equals(Material.AGGREGATE) || downState.material.equals(Material.SOIL)) && downState.material.blocksMovement()
    }
}
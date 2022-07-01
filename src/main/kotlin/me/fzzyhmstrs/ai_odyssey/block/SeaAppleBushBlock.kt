package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.minecraft.block.BlockState
import net.minecraft.block.FluidFillable
import net.minecraft.block.SweetBerryBushBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class SeaAppleBushBlock(settings: Settings): SweetBerryBushBlock(settings), FluidFillable {

    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.8, 0.8, 0.8))
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
}
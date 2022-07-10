package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineItemLockBlockEntity
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchLock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@Suppress("DEPRECATION")
class CrystallineItemLockBlock(settings: Settings): AbstractLockBlock(settings), SwitchLock {

    companion object {
        val HAS_ITEM: BooleanProperty = BooleanProperty.of("has_item")
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrystallineItemLockBlockEntity(pos, state)
    }

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (world.isClient) return super.onUse(state, world, pos, player, hand, hit)
        val chkEntity = world.getBlockEntity(pos)
        if (chkEntity != null){
            if (chkEntity is CrystallineItemLockBlockEntity){
                val stack = player.getStackInHand(hand)
                val item = stack.item
                if (chkEntity.trySetHeldItem(item)){
                    FacilityChimes.HIGH_SUCCESS.playSound(world, pos)
                    return ActionResult.SUCCESS
                }
            }
        }
        FacilityChimes.FAILURE.playSound(world, pos)
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(HAS_ITEM,false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(HAS_ITEM)
    }

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY)
        return if (entity == null || user == null){
            ActionResult.FAIL
        } else {
            val offHandStack = user.offHandStack
            if (!offHandStack.isEmpty) {
                entity.setKeyItem(offHandStack.item)
                user.sendMessage(TranslatableText("message.item_lock.item_set", offHandStack.item.name), false)
                FacilityChimes.CONFIG_SUCCESS.playSound(world, pos)
                ActionResult.SUCCESS
            } else {
                user.sendMessage(TranslatableText("message.item_lock.item_fail"), false)
                ActionResult.FAIL
            }
        }
    }

    override fun isUnlocked(world: World, pos: BlockPos): Boolean {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY)
        return entity?.isUnlocked() ?: false
    }
}
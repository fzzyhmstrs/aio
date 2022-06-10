package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineItemLockBlockEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

class CrystallineItemLockBlock(settings: Settings): AbstractLockBlock(settings) {

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
                if (stack.isOf(RegisterItem.FACILITY_CONFIGURATOR)){
                    val hand2 = when (hand){
                        Hand.MAIN_HAND->Hand.OFF_HAND
                        Hand.OFF_HAND->Hand.MAIN_HAND
                    }
                    val otherHandStack = player.getStackInHand(hand2)
                    return if (otherHandStack != ItemStack.EMPTY) {
                        val otherHandItem = otherHandStack.item
                        chkEntity.setKeyItem(otherHandItem)
                        player.sendMessage(TranslatableText("message.configurator.item_lock").append(otherHandItem.name), false)
                        FacilityChimes.CONFIG_SUCCESS.playSound(world, pos)
                        ActionResult.SUCCESS
                    } else {
                        super.onUse(state, world, pos, player, hand, hit)
                    }
                }
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
    
    companion object {
        val HAS_ITEM = BooleanProperty.of("has_item")
    }
}
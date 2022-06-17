package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineNumLockBlockEntity
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchLock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineNumLockBlock(settings: Settings): AbstractLockBlock(settings), SwitchLock {

    companion object{
        val LOCK_NUM: IntProperty = IntProperty.of("lock_num",0,9)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrystallineNumLockBlockEntity(pos, state)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LOCK_NUM)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(LOCK_NUM,0)
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
        if (world.isClient) super.onUse(state, world, pos, player, hand, hit)
        val stack = player.getStackInHand(hand)
        if (stack.isOf(RegisterItem.FACILITY_CONFIGURATOR)){
            val chkEntity = world.getBlockEntity(pos)
            if (chkEntity != null){
                if (chkEntity is CrystallineNumLockBlockEntity){
                    val num = state.get(LOCK_NUM)
                    chkEntity.setKeyNumber(num)
                    player.sendMessage(TranslatableText("message.configurator.num_lock").append(LiteralText(num.toString())), false)
                    FacilityChimes.CONFIG_SUCCESS.playSound(world, pos)
                    return ActionResult.SUCCESS
                }
            }
        }
        if (world.setBlockState(pos,state.cycle(LOCK_NUM))){
                FacilityChimes.NUM_CLICK.playSound(world, pos)
            return ActionResult.SUCCESS
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_NUM_LOCK_BLOCK_ENTITY)
        return if (entity == null || user == null){
            ActionResult.FAIL
        } else {
            val offHandStack = user.offHandStack
            if (!offHandStack.isEmpty) {
                val offHandCount = offHandStack.count
                val keyNumber = entity.setKeyNumber(offHandCount - 1)
                user.sendMessage(TranslatableText("message.num_lock.num_set", keyNumber.toString()), false)
                ActionResult.SUCCESS
            } else {
                user.sendMessage(TranslatableText("message.num_lock.num_fail"), false)
                ActionResult.FAIL
            }
        }
    }

    override fun isUnlocked(world: World, pos: BlockPos): Boolean {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_NUM_LOCK_BLOCK_ENTITY)
        return entity?.isUnlocked() ?: false
    }

}
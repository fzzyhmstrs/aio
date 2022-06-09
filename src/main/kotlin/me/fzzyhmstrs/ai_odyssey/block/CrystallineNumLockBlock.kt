package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineNumLockBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineNumLockBlock(settings: Settings): AbstractLockBlock(settings) {

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
        if (!world.isClient && world.setBlockState(pos,state.cycle(LOCK_NUM))){
                world.playSound(null,pos,SoundEvents.BLOCK_COMPARATOR_CLICK,SoundCategory.BLOCKS,1.0F,1.2F)
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    companion object{
        val LOCK_NUM: IntProperty = IntProperty.of("lock_num",0,9)
    }

}
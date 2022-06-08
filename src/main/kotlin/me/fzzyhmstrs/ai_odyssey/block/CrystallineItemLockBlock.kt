package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineItemLockBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class CrystallineItemLockBlock(settings: Settings): BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrystallineItemLockBlockEntity(pos, state)
    }
}
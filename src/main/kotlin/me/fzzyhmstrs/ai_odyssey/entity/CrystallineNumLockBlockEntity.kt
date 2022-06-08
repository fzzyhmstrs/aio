package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineNumLockBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.CRYSTALLINE_NUM_LOCK_BLOCK_ENTITY, pos, state) {
}
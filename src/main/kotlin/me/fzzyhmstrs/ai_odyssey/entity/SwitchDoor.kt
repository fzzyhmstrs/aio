package me.fzzyhmstrs.ai_odyssey.entity

import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface SwitchDoor {

    fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState)

}
package me.fzzyhmstrs.ai_odyssey.configurator

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface SwitchLock: ConfiguratorInteractive {

    fun isUnlocked(world: World, pos: BlockPos): Boolean

}
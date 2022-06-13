package me.fzzyhmstrs.ai_odyssey.configurator

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface ConfiguratorInteractive {

    fun interactWithConfigurator(world: World, user: PlayerEntity?, stack: ItemStack, pos: BlockPos, state: BlockState): ActionResult

}
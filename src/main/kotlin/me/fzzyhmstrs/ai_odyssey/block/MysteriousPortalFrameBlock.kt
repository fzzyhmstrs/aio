package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MysteriousPortalFrameBlock(settings: Settings): Block(settings), SwitchDoor {

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        TODO("Not yet implemented")
    }

    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

    override fun doorType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.PORTAL
    }
}
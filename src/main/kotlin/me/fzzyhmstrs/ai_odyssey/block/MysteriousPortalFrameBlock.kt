package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.SwitchDoor
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MysteriousPortalFrameBlock(settings: Settings): Block(settings), SwitchDoor {
    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

    override fun getType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.PORTAL
    }
}
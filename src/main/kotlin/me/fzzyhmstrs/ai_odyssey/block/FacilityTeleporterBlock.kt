package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.FacilityTeleporterBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.SwitchDoor
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FacilityTeleporterBlock(settings: Settings): BlockWithEntity(settings), SwitchDoor {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return FacilityTeleporterBlockEntity(pos, state)
    }

    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

    override fun getType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.TELEPORTER
    }
}
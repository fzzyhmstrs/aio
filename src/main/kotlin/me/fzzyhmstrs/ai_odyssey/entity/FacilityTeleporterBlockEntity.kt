package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FacilityTeleporterBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.FACILITY_TELEPORTER_BLOCK_ENTITY,pos, state), SwitchDoor {

    private var destination: BlockPos? = null

    fun setDestination(pos: BlockPos){
        destination = pos
        markDirty()
    }



    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

}
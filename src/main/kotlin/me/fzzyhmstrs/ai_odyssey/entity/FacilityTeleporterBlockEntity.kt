package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos

class FacilityTeleporterBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.FACILITY_TELEPORTER_BLOCK_ENTITY,pos, state) {

    private var destination: BlockPos? = null

    fun setDestination(pos: BlockPos){
        destination = pos
        markDirty()
    }

    fun getDestination(): BlockPos?{
        return destination
    }

    override fun readNbt(nbt: NbtCompound?) {
        super.readNbt(nbt)
    }

}
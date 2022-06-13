package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MysteriousPortalFrameBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY,pos, state) {

    private var portalKey: Boolean = false
    private var frameList: List<BlockPos> = listOf()

    fun isPortalKey(): Boolean{
        return portalKey
    }

    fun setAsPortalKey(list: List<BlockPos>){
        frameList = list
        portalKey = true
        markDirty()
    }

}
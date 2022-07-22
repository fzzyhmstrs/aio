package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_core.nbt_util.Nbt
import me.fzzyhmstrs.amethyst_core.nbt_util.NbtKeys
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.math.BlockPos

class MysteriousPortalFrameBlockEntity(pos: BlockPos, state: BlockState):RotatableFacilityBlockEntity(RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY,pos, state) {

    private var portalKey: Boolean = false
    private var frameList: List<BlockPos> = listOf()
    private var portalList: List<BlockPos> = listOf()

    fun isPortalKey(): Boolean{
        return portalKey
    }

    fun setAsPortalKey(framePosList: List<BlockPos>, portalPosList: List<BlockPos>){
        frameList = framePosList
        portalList = portalPosList
        portalKey = true
        markDirty()
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Nbt.writeBoolNbt(NbtKeys.PORTAL_KEY.str(),portalKey,nbt)
        writeListAsOffset(nbt,NbtKeys.FRAME_POS.str(),NbtKeys.FRAME_LIST.str(),frameList,this.pos)
        writeListAsOffset(nbt,NbtKeys.PORTAL_POS.str(),NbtKeys.FRAME_LIST.str(),portalList,this.pos)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        if (nbt.contains(NbtKeys.PORTAL_KEY.str())){
            portalKey = Nbt.readBoolNbt(NbtKeys.PORTAL_KEY.str(),nbt)
        }
        val tempFrameList: MutableList<BlockPos> = mutableListOf()
        readListFromOffset(nbt,NbtKeys.FRAME_POS.str(),NbtKeys.FRAME_LIST.str(),tempFrameList,this.pos)
        frameList = tempFrameList
        val tempPortalList: MutableList<BlockPos> = mutableListOf()
        readListFromOffset(nbt,NbtKeys.PORTAL_POS.str(),NbtKeys.PORTAL_LIST.str(),tempPortalList,this.pos)
        portalList = tempPortalList
    }

}
package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_core.nbt_util.Nbt
import me.fzzyhmstrs.amethyst_core.nbt_util.NbtKeys
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.math.BlockPos

class MysteriousPortalFrameBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY,pos, state) {

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
        if (frameList.isNotEmpty()){
            val nbtList = NbtList()
            frameList.forEach {
                val newNbtPos = NbtCompound()
                Nbt.writeBlockPos(NbtKeys.FRAME_POS.str(),it,newNbtPos)
                nbtList.add(newNbtPos)
            }
            nbt.put(NbtKeys.FRAME_LIST.str(), nbtList)
        }
        if (portalList.isNotEmpty()){
            val nbtList = NbtList()
            portalList.forEach {
                val newNbtPos = NbtCompound()
                Nbt.writeBlockPos(NbtKeys.PORTAL_POS.str(),it,newNbtPos)
                nbtList.add(newNbtPos)
            }
            nbt.put(NbtKeys.PORTAL_LIST.str(), nbtList)
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        if (nbt.contains(NbtKeys.PORTAL_KEY.str())){
            portalKey = Nbt.readBoolNbt(NbtKeys.PORTAL_KEY.str(),nbt)
        }
        if (nbt.contains(NbtKeys.FRAME_LIST.str())){
            val tempFrameList: MutableList<BlockPos> = mutableListOf()
            val nbtList = Nbt.readNbtList(nbt, NbtKeys.FRAME_LIST.str())
            nbtList.forEach {
                val nbtEl = it as NbtCompound
                if (nbtEl.contains(NbtKeys.FRAME_POS.str())){
                    tempFrameList.add(Nbt.readBlockPos(NbtKeys.FRAME_POS.str(),nbtEl))
                }
            }
            frameList = tempFrameList
        }
        if (nbt.contains(NbtKeys.PORTAL_LIST.str())){
            val tempPortalList: MutableList<BlockPos> = mutableListOf()
            val nbtList = Nbt.readNbtList(nbt, NbtKeys.PORTAL_LIST.str())
            nbtList.forEach {
                val nbtEl = it as NbtCompound
                if (nbtEl.contains(NbtKeys.PORTAL_POS.str())){
                    tempPortalList.add(Nbt.readBlockPos(NbtKeys.PORTAL_POS.str(),nbtEl))
                }
            }
            portalList = tempPortalList
        }
    }

}
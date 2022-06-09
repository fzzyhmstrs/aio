package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import me.fzzyhmstrs.amethyst_imbuement.util.NbtKeys
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry

class CrystallineItemLockBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY,pos, state), SwitchLock {

    private var keyItem: Item? = null
    private var heldItem: Item? = null

    fun setKeyItem(item: Item){
        keyItem = item
        markDirty()
    }

    fun trySetHeldItem(item: Item): Boolean{
        return if (keyItem != null && item == keyItem){
            heldItem = item
            markDirty()
            heldItemSuccess()
            true
        } else {
            heldItemFailure()
            false
        }
    }

    private fun heldItemSuccess(){
        TODO()
    }

    private fun heldItemFailure(){
        TODO()
    }

    override fun isUnlocked(): Boolean {
        return heldItem == keyItem
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        if (keyItem != null) {
            Nbt.writeIntNbt(NbtKeys.KEY_ITEM.str(),Registry.ITEM.getRawId(keyItem),nbt)
        }
        if (heldItem != null) {
            Nbt.writeIntNbt(NbtKeys.HELD_ITEM.str(), Registry.ITEM.getRawId(keyItem), nbt)
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        if (nbt.contains(NbtKeys.KEY_ITEM.str())){
            keyItem = Registry.ITEM.get(Nbt.readIntNbt(NbtKeys.KEY_ITEM.str(),nbt))
        }
        if (nbt.contains(NbtKeys.HELD_ITEM.str())){
            heldItem = Registry.ITEM.get(Nbt.readIntNbt(NbtKeys.HELD_ITEM.str(),nbt))
        }
    }
}
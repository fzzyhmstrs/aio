package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.decoration.ItemFrameEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos

class CrystallineItemLockBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY,pos, state), SwitchLockEntity {

    private var keyItem: Item? = null
    private var heldItem: Item = Items.AIR

    fun setKeyItem(item: Item){
        keyItem = item
        markDirty()
    }

    fun trySetHeldItem(item: Item): Boolean{
        return if (item == keyItem){
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
        TODO("Not yet implemented")
    }

}
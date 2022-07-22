package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.amethyst_core.nbt_util.Nbt
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos

open class RotatableFacilityBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState): BlockEntity(type, pos, state) {

    companion object{
        val ROTATION: DirectionProperty = Properties.HORIZONTAL_FACING
    }

    private var rotationDelta: Int = 0

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        val facing = cachedState.get(ROTATION)
        Nbt.writeIntNbt("rotation",facing.horizontal, nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        val facing = cachedState.get(ROTATION)
        val rotation = Nbt.readIntNbt("rotation",nbt)
        rotationDelta = facing.horizontal - rotation
    }

    protected fun writeListAsOffset(nbt: NbtCompound, elementKey: String, listKey: String, list: List<BlockPos>, pos: BlockPos){
        if (list.isNotEmpty()){
            val nbtList = NbtList()
            list.forEach {
                val nbtEl = NbtCompound()
                Nbt.writeBlockPos(elementKey,it.subtract(pos),nbtEl) //writing an offset rather than an absolute
                nbtList.add(nbtEl)
            }
            nbt.put(listKey,nbtList)
        }
    }

    protected fun readListFromOffset(nbt: NbtCompound, elementKey: String, listKey: String, list: MutableList<BlockPos>, pos: BlockPos){
        if (nbt.contains(listKey)){
            val nbtList = Nbt.readNbtList(nbt, listKey)
            for (el in nbtList){
                val compound = el as NbtCompound
                if (compound.contains(elementKey)){
                    val tempPos = Nbt.readBlockPos(elementKey,compound)
                    when(rotationDelta) { //recomposing list position based on rotation amount and new (or not new) position
                        0-> list.add(BlockPos(tempPos.x, tempPos.y, tempPos.z).add(pos))
                        1-> list.add(BlockPos(tempPos.z * -1, tempPos.y, tempPos.x).add(pos))
                        -1-> list.add(BlockPos(tempPos.z, tempPos.y, tempPos.x * -1).add(pos))
                        2-> list . add (BlockPos(tempPos.x * -1, tempPos.y, tempPos.z * -1).add(pos))
                        else-> list.add(BlockPos(tempPos.x, tempPos.y, tempPos.z).add(pos))
                    }
                }
            }
        }

    }

}


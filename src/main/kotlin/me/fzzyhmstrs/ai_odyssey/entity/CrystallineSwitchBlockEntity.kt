package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.block.CrystallineSwitchBlock
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchLock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEvent
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import me.fzzyhmstrs.amethyst_imbuement.util.NbtKeys
import net.minecraft.block.Block.NOTIFY_LISTENERS
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineSwitchBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY,pos, state) {

    private val locks: MutableList<BlockPos> = mutableListOf()
    private val doors: MutableList<BlockPos> = mutableListOf()
    private var unlocked = true

    fun getLocks(): List<BlockPos>{
        return locks
    }

    fun addLock(pos: BlockPos): Boolean{
        return if (!locks.contains(pos)) {
            locks.add(pos)
            markDirty()
            true
        } else {
            false
        }
    }

    fun getDoors(): List<BlockPos>{
        return doors
    }

    fun addDoor(pos: BlockPos): Boolean{
        return if (!doors.contains(pos)) {
            doors.add(pos)
            markDirty()
            true
        } else {
            false
        }
    }

    fun isUnlocked(): Boolean{
        return unlocked
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        if (locks.isNotEmpty()){
            val nbtList = NbtList()
            locks.forEach {
                val nbtEl = NbtCompound()
                Nbt.writeBlockPos(NbtKeys.LOCK_POS.str(),it,nbtEl)
                nbtList.add(nbtEl)
            }
            nbt.put(NbtKeys.LOCKS.str(),nbtList)
        }
        if (doors.isNotEmpty()){
            val nbtList = NbtList()
            doors.forEach {
                val nbtEl = NbtCompound()
                Nbt.writeBlockPos(NbtKeys.DOOR_POS.str(),it,nbtEl)
                nbtList.add(nbtEl)
            }
            nbt.put(NbtKeys.DOORS.str(),nbtList)
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        if (nbt.contains(NbtKeys.LOCKS.str())){
            val nbtList = Nbt.readNbtList(nbt,NbtKeys.LOCKS.str())
            for (el in nbtList){
                val compound = el as NbtCompound
                if (compound.contains(NbtKeys.LOCK_POS.str())){
                    locks.add(Nbt.readBlockPos(NbtKeys.LOCK_POS.str(),compound))
                }
            }
        }
        if (nbt.contains(NbtKeys.DOORS.str())){
            val nbtList = Nbt.readNbtList(nbt,NbtKeys.DOORS.str())
            for (el in nbtList){
                val compound = el as NbtCompound
                if (compound.contains(NbtKeys.DOOR_POS.str())){
                    doors.add(Nbt.readBlockPos(NbtKeys.DOOR_POS.str(),compound))
                }
            }
        }

    }

    companion object{
        fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CrystallineSwitchBlockEntity) {
            if (RegisterEvent.ticker_totem.isNotReady()) return
            if (blockEntity.locks.isNotEmpty()){
                var bl = true
                blockEntity.locks.forEach {
                    val lockEntity = world.getBlockEntity(it)
                    if (lockEntity is SwitchLock){
                        bl = bl && lockEntity.isUnlocked(world, pos)
                    }
                }
                blockEntity.unlocked = bl
                val bl2 = CrystallineSwitchBlock.isSwitchLit(state)
                if (bl2 != null){
                    if (bl2 && !bl){
                        world.setBlockState(pos, state.with(CrystallineSwitchBlock.LIT,false), NOTIFY_LISTENERS)
                    } else if (!bl2 && bl){
                        world.setBlockState(pos, state.with(CrystallineSwitchBlock.LIT,true), NOTIFY_LISTENERS)
                        world.playSound(null, pos,SoundEvents.BLOCK_NOTE_BLOCK_CHIME,SoundCategory.BLOCKS,2.0f, 2.0f)
                    }
                }
            }
        }
    }


}
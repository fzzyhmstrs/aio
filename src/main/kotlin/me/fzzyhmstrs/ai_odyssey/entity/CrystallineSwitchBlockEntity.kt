package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.block.CrystallineSwitchBlock
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchLock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_core.nbt_util.Nbt
import me.fzzyhmstrs.amethyst_core.nbt_util.NbtKeys
import me.fzzyhmstrs.amethyst_core.registry.EventRegistry
import net.minecraft.block.Block.NOTIFY_LISTENERS
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineSwitchBlockEntity(pos: BlockPos, state: BlockState): RotatableFacilityBlockEntity(RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY,pos, state) {

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
        val thisPos = this.pos
        writeListAsOffset(nbt,NbtKeys.LOCK_POS.str(), NbtKeys.LOCKS.str(), locks,thisPos)
        writeListAsOffset(nbt,NbtKeys.DOOR_POS.str(), NbtKeys.DOORS.str(), doors,thisPos)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        val thisPos = this.pos
        readListFromOffset(nbt,NbtKeys.LOCK_POS.str(),NbtKeys.LOCKS.str(),locks,thisPos)
        readListFromOffset(nbt,NbtKeys.DOOR_POS.str(),NbtKeys.DOORS.str(),doors, thisPos)

    }

    companion object{
        fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CrystallineSwitchBlockEntity) {
            if (EventRegistry.ticker_20.isNotReady()) return
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
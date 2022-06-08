package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.block.CrystallineSwitchBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_imbuement.entity.AltarOfExperienceBlockEntity
import net.minecraft.block.Block.NOTIFY_LISTENERS
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrystallineSwitchBlockEntity(pos: BlockPos, state: BlockState):BlockEntity(RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY,pos, state) {

    private val locks: MutableList<BlockPos> = mutableListOf()
    private val doors: MutableList<BlockPos> = mutableListOf()

    fun getLocks(): List<BlockPos>{
        return locks
    }

    fun addLock(pos: BlockPos){
        locks.add(pos)
        markDirty()
    }

    fun getDoors(): List<BlockPos>{
        return doors
    }

    fun addDoor(pos: BlockPos){
        doors.add(pos)
        markDirty()
    }

    companion object{
        fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CrystallineSwitchBlockEntity) {
            if (blockEntity.locks.isNotEmpty()){
                var bl = true
                blockEntity.locks.forEach {
                    val lockEntity = world.getBlockEntity(it)
                    if (lockEntity is SwitchLockEntity){
                        bl = bl && lockEntity.isUnlocked()
                    }
                }
                val bl2 = CrystallineSwitchBlock.isSwitchLit(state)
                if (bl2 != null){
                    if (bl2 && !bl){
                        world.setBlockState(pos, state.with(CrystallineSwitchBlock.LIT,false), NOTIFY_LISTENERS)
                    } else if (!bl2 && bl){
                        world.setBlockState(pos, state.with(CrystallineSwitchBlock.LIT,true), NOTIFY_LISTENERS)
                    }
                }
            }
        }
    }


}
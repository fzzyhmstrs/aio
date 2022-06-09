package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.block.CrystallineNumLockBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import me.fzzyhmstrs.amethyst_imbuement.util.NbtKeys
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class CrystallineNumLockBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(RegisterEntity.CRYSTALLINE_NUM_LOCK_BLOCK_ENTITY, pos, state), SwitchLock {

    private var keyNumber: Int = -1

    fun setKeyNumber(number: Int){
        keyNumber = MathHelper.clamp(number,0,9)
        markDirty()
    }

    override fun isUnlocked(): Boolean {
        val numOpt = cachedState.getOrEmpty(CrystallineNumLockBlock.LOCK_NUM)
        val chkNumber = numOpt.orElse(-2)
        return keyNumber == chkNumber
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Nbt.writeIntNbt(NbtKeys.KEY_NUM.str(),keyNumber,nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        keyNumber = Nbt.readIntNbt(NbtKeys.KEY_NUM.str(),nbt)

    }
}
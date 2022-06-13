package me.fzzyhmstrs.ai_odyssey.configurator

import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import me.fzzyhmstrs.amethyst_imbuement.AI
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos

interface ConfiguratorPaired {

    val primedKey: String
    val primedPosKey: String
    val parentKey: String

    fun <T: BlockEntity> setParent(entity: T, playerEntity: PlayerEntity?, stack: ItemStack) {
        val nbt = stack.orCreateNbt
        val primed = isPrimed(nbt, primedKey)
        val primedPos = getPrimedPos(nbt, primedPosKey)
        if (primed && entity.pos == primedPos) {
            val id = setParentId(nbt, parentKey)
            setParentToId(entity, id)
            playerEntity?.sendMessage(
                TranslatableText("message.configurator.configurator_pairing").append(entity.pos.toShortString()),
                false
            )
            entity.world?.let { FacilityChimes.SUCCESS.playSound(it, entity.pos) }
            setPrimed(nbt,primedKey, false)
            setPrimedPos(nbt,primedPosKey)
        } else {
            setPrimed(nbt,primedKey, true)
            setPrimedPos(nbt,primedPosKey, entity.pos)
            playerEntity?.sendMessage(TranslatableText("message.configurator.configurator_pairing_primed"), false)
            if (hasParent(nbt, parentKey)) {
                playerEntity?.sendMessage(
                    TranslatableText("message.configurator.configurator_pairing_primed_2").formatted(
                        Formatting.RED
                    ), false
                )
            }
        }
    }

    companion object{
        private val parentPairs: MutableMap<Int, BlockEntity> = mutableMapOf()

        fun setParentToId(entity: BlockEntity, id: Int){
            parentPairs[id] = entity
        }

        fun getParentFromId(id: Int): BlockEntity?{
            return parentPairs[id]
        }

        fun hasParent(nbt: NbtCompound, key: String): Boolean{
            return nbt.contains(key)
        }
        fun setParentId(nbt: NbtCompound, key: String): Int{
            val id = AI.aiRandom().nextInt(Int.MAX_VALUE)
            Nbt.writeIntNbt(key, id, nbt)
            return id
        }
        fun getParentId(nbt: NbtCompound, key: String): Int{
            return if (nbt.contains(key)){
                Nbt.readIntNbt(key, nbt)
            } else {
                -1
            }
        }
        fun isPrimed(nbt: NbtCompound, key: String): Boolean{
            return if (nbt.contains(key)){
                Nbt.readBoolNbt(key,nbt)
            } else {
                false
            }
        }
        fun setPrimed(nbt: NbtCompound, key: String, bl: Boolean){
            Nbt.writeBoolNbt(key,bl, nbt)
        }
        fun getPrimedPos(nbt: NbtCompound, key: String): BlockPos {
            return if (nbt.contains(key)){
                Nbt.readBlockPos(key, nbt)
            } else {
                BlockPos.ORIGIN
            }
        }
        fun setPrimedPos(nbt: NbtCompound, key: String, pos: BlockPos = BlockPos.ORIGIN){
            Nbt.writeBlockPos(key, pos, nbt)
        }

    }



}
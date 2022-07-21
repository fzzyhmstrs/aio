package me.fzzyhmstrs.ai_odyssey.augment

import me.fzzyhmstrs.ai_odyssey.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import me.fzzyhmstrs.amethyst_core.trinket_util.base_augments.AbstractEquipmentAugment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry

class PressureResistanceAugment(weight: Rarity, mxLvl: Int = 1, vararg slot: EquipmentSlot): AbstractEquipmentAugment(weight, mxLvl,
    EnchantmentTarget.ARMOR, *slot) {

    override fun isAcceptableItem(stack: ItemStack): Boolean {
        return stack.item is ArmorItem
    }

    override fun acceptableItemStacks(): MutableList<ItemStack> {
        val list = mutableListOf<ItemStack>()
        val entries = Registry.ITEM.indexedEntries
        for (entry in entries){
            val item = entry.value()
            if (item is ArmorItem){
                list.add(ItemStack(item,1))
            }
        }
        return list
    }

    override fun equipmentEffect(user: LivingEntity, level: Int, stack: ItemStack) {
        EffectQueue.addStatusToQueue(user, RegisterStatus.PRESSURE_RESISTANCE,260,level - 1)
    }
}
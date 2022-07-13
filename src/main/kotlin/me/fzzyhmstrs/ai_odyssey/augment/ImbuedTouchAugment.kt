package me.fzzyhmstrs.ai_odyssey.augment

import me.fzzyhmstrs.amethyst_core.trinket_util.base_augments.BaseAugment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

class ImbuedTouchAugment(weight: Rarity, maxLevel: Int, vararg slot: EquipmentSlot): BaseAugment(weight,maxLevel,EnchantmentTarget.DIGGER, *slot) {

}
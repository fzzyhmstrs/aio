package me.fzzyhmstrs.ai_odyssey.armor

import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

class MysticArmorMaterial: ArmorMaterial {
    private val BASE_DURABILITY = intArrayOf(13, 15, 16, 11)
    private val PROTECTION_VALUES = intArrayOf(5, 9, 10, 5)

    override fun getName(): String = "aio_mystic"
    override fun getEquipSound(): SoundEvent = SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE
    override fun getRepairIngredient(): Ingredient = Ingredient.ofItems(RegisterItem.MYSTIC_QUINTESSENCE)
    override fun getEnchantability(): Int = 25
    override fun getProtectionAmount(slot: EquipmentSlot): Int = PROTECTION_VALUES[slot.entitySlotId]
    override fun getDurability(slot: EquipmentSlot): Int = PROTECTION_VALUES[slot.entitySlotId] * 62
    override fun getKnockbackResistance(): Float = 0.15F
    override fun getToughness(): Float = 4.0F
}
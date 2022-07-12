package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.armor.MysticArmorMaterial
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterArmor {

    val MYSTIC_ARMOR_MATERIAL = MysticArmorMaterial()
    val MYSTIC_HELMET = ArmorItem(MYSTIC_ARMOR_MATERIAL,EquipmentSlot.HEAD, Item.Settings().group(ItemGroup.COMBAT))
    val MYSTIC_CHESTPLATE = ArmorItem(MYSTIC_ARMOR_MATERIAL,EquipmentSlot.CHEST, Item.Settings().group(ItemGroup.COMBAT))
    val MYSTIC_LEGGINGS = ArmorItem(MYSTIC_ARMOR_MATERIAL,EquipmentSlot.LEGS, Item.Settings().group(ItemGroup.COMBAT))
    val MYSTIC_BOOTS = ArmorItem(MYSTIC_ARMOR_MATERIAL,EquipmentSlot.FEET, Item.Settings().group(ItemGroup.COMBAT))

    fun registerAll(){
        Registry.register(Registry.ITEM, Identifier(AIO.MOD_ID,"mystic_helmet"), MYSTIC_HELMET)
        Registry.register(Registry.ITEM, Identifier(AIO.MOD_ID,"mystic_chestplate"), MYSTIC_CHESTPLATE)
        Registry.register(Registry.ITEM, Identifier(AIO.MOD_ID,"mystic_leggings"), MYSTIC_LEGGINGS)
        Registry.register(Registry.ITEM, Identifier(AIO.MOD_ID,"mystic_boots"), MYSTIC_BOOTS)
    }
}
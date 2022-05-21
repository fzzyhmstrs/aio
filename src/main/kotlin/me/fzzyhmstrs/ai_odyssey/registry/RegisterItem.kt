@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterItem
import me.fzzyhmstrs.amethyst_imbuement.tool.ScepterLvl1ToolMaterial
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry


object RegisterItem {

    var regItem: MutableMap<String, Item> = mutableMapOf()

    //materials and crafting items
    val ALEXANDRITE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ RegisterItem.regItem["alexandrite"] = it}
    val BLOODSTONE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ RegisterItem.regItem["bloodstone"] = it}
    val SERPENTINE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ RegisterItem.regItem["serpentine"] = it}
    val MYSTIC_FRAGMENT = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC)).also{ RegisterItem.regItem["mystic_fragment"] = it}
    val MYSTIC_QUINTESSENCE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC)).also{ RegisterItem.regItem["mystic_quintessence"] = it}

    //tools and weapons
    val RESPLENDENT_SCEPTER = ScepterItem(ScepterLvl1ToolMaterial, FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).also{ RegisterItem.regItem["resplendent_scepter"] = it}

    fun registerAll() {
        for (k in RegisterItem.regItem.keys){
            Registry.register(Registry.ITEM,Identifier(AIO.MOD_ID,k), RegisterItem.regItem[k])
        }
        RegisterItem.regItem.clear()
    }
}
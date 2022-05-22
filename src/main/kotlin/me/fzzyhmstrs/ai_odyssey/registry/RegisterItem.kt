@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.item.StrangeMapItem
import me.fzzyhmstrs.ai_odyssey.tool.ScepterLvl4ToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.item.CustomFlavorItem
import me.fzzyhmstrs.amethyst_imbuement.item.ImbuedJewelryItem
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry


object RegisterItem {

    private val regItem: MutableMap<String, Item> = mutableMapOf()

    //materials and crafting items
    val ALEXANDRITE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["alexandrite"] = it}
    val BLOODSTONE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["bloodstone"] = it}
    val SERPENTINE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["serpentine"] = it}
    val MYSTIC_FRAGMENT = CustomFlavorItem(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC),"mystic_fragment",true).also{ regItem["mystic_fragment"] = it}
    val MYSTIC_QUINTESSENCE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC)).also{ regItem["mystic_quintessence"] = it}

    //tools and weapons
    val RESPLENDENT_SCEPTER = ScepterItem(ScepterLvl4ToolMaterial, FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).also{ regItem["resplendent_scepter"] = it}

    //flora and fauna


    //food and other practical items

    //trinkets and baubles
    val DIVINE_CORONET = ImbuedJewelryItem(FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC),"divine_coronet").also{ regItem["divine_coronet"] = it}
    val RESPLENDENT_RARITY = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC)).also{ regItem["resplendent_rarity"] = it}
    val STRANGE_MAP = StrangeMapItem(FabricItemSettings().group(ItemGroup.MISC)).also{ regItem["strange_scrap"] = it}
    val STRANGE_SCRAP = CustomFlavorItem(FabricItemSettings().group(ItemGroup.MISC),"strange_scrap",false).also{ regItem["strange_scrap"] = it}

    fun registerAll() {
        for (k in regItem.keys){
            Registry.register(Registry.ITEM,Identifier(AIO.MOD_ID,k), regItem[k])
        }
        regItem.clear()
    }
}
@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry


object RegisterItem {
    val EXAMPLE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON))

    fun registerAll() {
        Registry.register(Registry.ITEM, Identifier(AIO.MOD_ID,"example"), EXAMPLE)
    }
}
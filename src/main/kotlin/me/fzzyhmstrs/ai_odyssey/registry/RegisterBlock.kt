package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.block.SeaBambooBlock
import me.fzzyhmstrs.ai_odyssey.block.SeaBambooPlantBlock
import me.fzzyhmstrs.amethyst_imbuement.AI
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterBlock {

    val SEA_BAMBOO = SeaBambooBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO_PLANT = SeaBambooPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))

    fun registerAll(){
        registerBlock("sea_bamboo", SEA_BAMBOO, ItemGroup.DECORATIONS)
        registerBlock("sea_bamboo_plant", SEA_BAMBOO_PLANT, ItemGroup.DECORATIONS)
    }

    private fun registerBlock(path: String, block: Block, itemGroup: ItemGroup){
        Registry.register(Registry.BLOCK, Identifier(AI.MOD_ID, path), block)
        Registry.register(
            Registry.ITEM, Identifier(AI.MOD_ID,path), BlockItem(block,
                FabricItemSettings().group(itemGroup))
        )
    }
}
package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.block.*
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

    val BULL_KELP = BullKelpBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val BULL_KELP_PLANT = BullKelpPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val BULL_KELP_STREAMER = BullKelpStreamerBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO = SeaBambooBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO_PLANT = SeaBambooPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val WIRE_WEED = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val TURTLE_GRASS = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))

    fun registerAll(){
        registerBlock("sea_bamboo", SEA_BAMBOO, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("sea_bamboo_plant", SEA_BAMBOO_PLANT)
        registerBlock("bull_kelp", BULL_KELP, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("bull_kelp_plant", BULL_KELP_PLANT)
        registerBlockWithOtherItem("bull_kelp_streamer", BULL_KELP_STREAMER)
        registerBlock("turtle_grass", WIRE_WEED, ItemGroup.DECORATIONS)
        registerBlock("wire_weed", TURTLE_GRASS, ItemGroup.DECORATIONS)
    }

    private fun registerBlock(path: String, block: Block, itemGroup: ItemGroup){
        Registry.register(Registry.BLOCK, Identifier(AI.MOD_ID, path), block)
        Registry.register(
            Registry.ITEM, Identifier(AI.MOD_ID,path), BlockItem(block,
                FabricItemSettings().group(itemGroup))
        )
    }
    private fun registerBlockWithOtherItem(path: String, block: Block){
        Registry.register(Registry.BLOCK, Identifier(AI.MOD_ID, path), block)
    }
}
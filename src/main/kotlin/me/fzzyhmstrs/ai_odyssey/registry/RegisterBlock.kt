@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.block.*
import me.fzzyhmstrs.amethyst_imbuement.AI
import me.fzzyhmstrs.amethyst_imbuement.block.ExperienceBushBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.block.SandBlock
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
    val CALCITE_SEDIMENT = SandBlock(0xE2D7C5,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))
    val QUARTZITE_SAND = SandBlock(0xEDEBDE,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))
    val SEA_APPLE_BUSH = SeaAppleBlock(FabricBlockSettings.of(Material.REPLACEABLE_UNDERWATER_PLANT, MapColor.LICHEN_GREEN).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH))
    val SEA_BAMBOO = SeaBambooBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO_PLANT = SeaBambooPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val WIRE_WEED = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val TURTLE_GRASS = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))

    fun registerAll(){
        registerBlock("bull_kelp", BULL_KELP, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("bull_kelp_plant", BULL_KELP_PLANT)
        registerBlockWithOtherItem("bull_kelp_streamer", BULL_KELP_STREAMER)
        registerBlock("calcite_sediment", CALCITE_SEDIMENT, ItemGroup.BUILDING_BLOCKS)
        registerBlock("quartzite_sand", QUARTZITE_SAND, ItemGroup.BUILDING_BLOCKS)
        registerBlockWithOtherItem("sea_apple_bush", SEA_APPLE_BUSH)
        registerBlock("sea_bamboo", SEA_BAMBOO, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("sea_bamboo_plant", SEA_BAMBOO_PLANT)
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
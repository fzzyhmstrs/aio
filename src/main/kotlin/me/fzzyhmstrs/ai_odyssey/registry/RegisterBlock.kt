@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.block.*
import me.fzzyhmstrs.amethyst_imbuement.AI
import me.fzzyhmstrs.amethyst_imbuement.block.ExperienceBushBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.*
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterBlock {

    //grasses and other plant life
    val BULL_KELP = BullKelpBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val BULL_KELP_PLANT = BullKelpPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val BULL_KELP_STREAMER = BullKelpStreamerBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_APPLE_BUSH = SeaAppleBlock(FabricBlockSettings.of(Material.REPLACEABLE_UNDERWATER_PLANT, MapColor.LICHEN_GREEN).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH))
    val SEA_BAMBOO = SeaBambooBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO_PLANT = SeaBambooPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val WIRE_WEED = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val TURTLE_GRASS = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))

    //basic world environment blocks
    val CALCITE_SEDIMENT = SandBlock(0xE2D7C5,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))
    val QUARTZITE_SAND = SandBlock(0xEDEBDE,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))

    //portal facility blocks
    val CRYSTALLINE_LANTERN = CrystallineLanternBlock(FabricBlockSettings.of(Material.GLASS, MapColor.OFF_WHITE).strength(0.3f).sounds(BlockSoundGroup.GLASS).luminance { state: BlockState ->
        CrystallineLanternBlock.STATE_TO_LUMINANCE.applyAsInt(state)
    })
    val CRYSTALLINE_SWITCH = CrystallineSwitchBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PINK).requiresTool().strength(50.0f, 1200.0f))
    val MYSTERIOUS_PORTAL_FRAME = Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f).dropsNothing())
    val PETROGLYPH = Block(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))
    val PETROGLYPH_CRACKED = PetroglyphCrackedBlock(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))
    val PETROGLYPH_CRUMBLED = PetroglyphCrackedBlock(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))
    val PETROGLYPH_RECIPE = PetroglyphRecipeBlock(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))

    //imbued deepslate
    val IMBUED_DEEPSLATE = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_COBBLED = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_POLISHED = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_BRICKS = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_BRICKS_CRACKED = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_CHISELED = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_TILES = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_TILES_CRACKED = ImbuedDeepslateBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_WALL_COBBLED = ImbuedDeepslateWallBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_WALL_POLISHED = ImbuedDeepslateWallBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_WALL_BRICK = ImbuedDeepslateWallBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_WALL_TILE = ImbuedDeepslateWallBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_STAIRS_COBBLED = ImbuedDeepslateStairsBlock(IMBUED_DEEPSLATE_COBBLED.defaultState,FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_STAIRS_POLISHED = ImbuedDeepslateStairsBlock(IMBUED_DEEPSLATE_POLISHED.defaultState,FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_STAIRS_BRICK = ImbuedDeepslateStairsBlock(IMBUED_DEEPSLATE_BRICKS.defaultState,FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_STAIRS_TILE = ImbuedDeepslateStairsBlock(IMBUED_DEEPSLATE_TILES.defaultState,FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_SLAB_COBBLED = ImbuedDeepslateSlabBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_SLAB_POLISHED = ImbuedDeepslateSlabBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_SLAB_BRICK = ImbuedDeepslateSlabBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_SLAB_TILE = ImbuedDeepslateSlabBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))

    fun registerAll(){
        registerBlock("bull_kelp", BULL_KELP, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("bull_kelp_plant", BULL_KELP_PLANT)
        registerBlockWithOtherItem("bull_kelp_streamer", BULL_KELP_STREAMER)
        registerBlock("calcite_sediment", CALCITE_SEDIMENT, ItemGroup.BUILDING_BLOCKS)
        registerBlock("crystalline_switch", CRYSTALLINE_SWITCH, ItemGroup.MISC)
        registerBlock("imbued_deepslate", IMBUED_DEEPSLATE, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_cobbled", IMBUED_DEEPSLATE_COBBLED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_polished", IMBUED_DEEPSLATE_POLISHED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_bricks", IMBUED_DEEPSLATE_BRICKS, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_bricks_cracked", IMBUED_DEEPSLATE_BRICKS_CRACKED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_chiseled", IMBUED_DEEPSLATE_CHISELED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_tiles", IMBUED_DEEPSLATE_TILES, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_tiles_cracked", IMBUED_DEEPSLATE_TILES_CRACKED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_wall_cobbled", IMBUED_DEEPSLATE_WALL_COBBLED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_wall_polished", IMBUED_DEEPSLATE_WALL_POLISHED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_wall_bricks", IMBUED_DEEPSLATE_WALL_BRICK, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_wall_tiles", IMBUED_DEEPSLATE_WALL_TILE, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_stairs_cobbled", IMBUED_DEEPSLATE_STAIRS_COBBLED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_stairs_polished", IMBUED_DEEPSLATE_STAIRS_POLISHED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_stairs_bricks", IMBUED_DEEPSLATE_STAIRS_BRICK, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_stairs_tiles", IMBUED_DEEPSLATE_STAIRS_TILE, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_slab_cobbled", IMBUED_DEEPSLATE_SLAB_COBBLED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_slab_polished", IMBUED_DEEPSLATE_SLAB_POLISHED, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_slab_bricks", IMBUED_DEEPSLATE_SLAB_BRICK, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_slab_tiles", IMBUED_DEEPSLATE_SLAB_TILE, ItemGroup.BUILDING_BLOCKS)
        registerBlock("mysterious_portal_frame", MYSTERIOUS_PORTAL_FRAME, ItemGroup.BUILDING_BLOCKS)
        registerBlock("petroglyph", PETROGLYPH, ItemGroup.DECORATIONS)
        registerBlock("petroglyph_cracked", PETROGLYPH_CRACKED, ItemGroup.DECORATIONS)
        registerBlock("petroglyph_crumbled", PETROGLYPH_CRUMBLED, ItemGroup.DECORATIONS)
        registerBlock("petroglyph_recipe", PETROGLYPH_RECIPE, ItemGroup.DECORATIONS)
        registerBlock("quartzite_sand", QUARTZITE_SAND, ItemGroup.BUILDING_BLOCKS)
        registerBlockWithOtherItem("sea_apple_bush", SEA_APPLE_BUSH)
        registerBlock("sea_bamboo", SEA_BAMBOO, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("sea_bamboo_plant", SEA_BAMBOO_PLANT)
        registerBlock("turtle_grass", WIRE_WEED, ItemGroup.DECORATIONS)
        registerBlock("wire_weed", TURTLE_GRASS, ItemGroup.DECORATIONS)
    }

    private fun registerBlock(path: String, block: Block, itemGroup: ItemGroup){
        Registry.register(Registry.BLOCK, Identifier(AIO.MOD_ID, path), block)
        Registry.register(
            Registry.ITEM, Identifier(AIO.MOD_ID,path), BlockItem(block,
                FabricItemSettings().group(itemGroup))
        )
    }
    private fun registerBlockWithOtherItem(path: String, block: Block){
        Registry.register(Registry.BLOCK, Identifier(AIO.MOD_ID, path), block)
    }
}
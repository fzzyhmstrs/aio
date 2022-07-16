@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.block.*
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
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
    val BULL_KELP_STREAMER = BullKelpStreamerBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS).dropsNothing())
    val MIDNIGHT_KELP = MidnightKelpBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).ticksRandomly().noCollision().strength(0.12f).sounds(
        BlockSoundGroup.WET_GRASS).luminance(11))
    val MIDNIGHT_KELP_PLANT = MidnightKelpPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).ticksRandomly().noCollision().strength(0.12f).sounds(
        BlockSoundGroup.WET_GRASS).luminance(11))
    val SARGASSUM = SargassumBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).strength(0.1f).sounds(
        BlockSoundGroup.WET_GRASS))
    val SARGASSUM_STREAMER = SargassumStreamerBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS).dropsNothing())
    val SEA_APPLE_BUSH = SeaAppleBushBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT, MapColor.LICHEN_GREEN).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH))
    val SEA_BAMBOO = SeaBambooBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val SEA_BAMBOO_PLANT = SeaBambooPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val WIRE_WEED = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.REPLACEABLE_UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))
    val TURTLE_GRASS = SeaGrassAlwaysShortBlock(FabricBlockSettings.of(Material.REPLACEABLE_UNDERWATER_PLANT).noCollision().breakInstantly().sounds(
        BlockSoundGroup.WET_GRASS))

    //user-created or artificial type blocks

    //basic world environment blocks
    val CALCITE_SEDIMENT = SandBlock(0xE2D7C5,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))
    val COBBLED_GABBRO = Block(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(1.5f, 6.0f).requiresTool())
    val GABBRO = Block(FabricBlockSettings.of(Material.STONE, MapColor.GRAY).strength(1.5f, 6.0f).requiresTool())
    val PRISMATIC_AMETHYST = PrismaticCrystalBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool())
    val PRISMATIC_GABBRO = PrismaticCrystalBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(1.5f, 6.0f).requiresTool())
    val PRISMATIC_PRISMARINE = PrismaticCrystalBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.LICHEN_GREEN).strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool())
    val PRISMATIC_QUARTZ = PrismaticCrystalBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.WHITE).strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool())
    val QUARTZITE_SAND = SandBlock(0xEDEBDE,FabricBlockSettings.of(Material.AGGREGATE, MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND))

    //portal facility blocks
    val ABERRATION_SPAWN = AberrationSpawnBlock(FabricBlockSettings.of(Material.AMETHYST).strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance(12))
    val CRYSTALLINE_LANTERN = CrystallineLanternBlock(FabricBlockSettings.of(Material.GLASS, MapColor.OFF_WHITE).strength(0.3f).sounds(BlockSoundGroup.GLASS).luminance { state: BlockState ->
        CrystallineLanternBlock.STATE_TO_LUMINANCE.applyAsInt(state)
    })
    val CRYSTALLINE_SWITCH = CrystallineSwitchBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PINK).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().strength(99.0f, 1200.0f).luminance { state: BlockState ->
        CrystallineSwitchBlock.STATE_TO_LUMINANCE.applyAsInt(state)
    })
    val CRYSTALLINE_SWITCH_PEDESTAL = CrystallineSwitchPedestal(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(1.5f, 6.0f))
    val CRYSTALLINE_ITEM_LOCK = CrystallineItemLockBlock(FabricBlockSettings.of(Material.STONE, MapColor.IRON_GRAY).requiresTool().strength(99.0f, 1200.0f))
    val CRYSTALLINE_NUM_LOCK = CrystallineNumLockBlock(FabricBlockSettings.of(Material.STONE, MapColor.IRON_GRAY).requiresTool().strength(99.0f, 1200.0f))
    val FACILITY_TELEPORTER = FacilityTeleporterBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).requiresTool().strength(99.0f, 1200.0f))
    val HARD_LIGHT_BARRIER = HardLightBarrierBlock(FabricBlockSettings.of(Material.GLASS).strength(-1.0F,3600000.0f).dropsNothing())
    val HARD_LIGHT_BARRIER_FRAME = Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f).dropsNothing())
    val MYSTERIOUS_PORTAL_FRAME = MysteriousPortalFrameBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f).dropsNothing())
    val PETROGLYPH = Block(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))
    val PETROGLYPH_CRACKED = PetroglyphCrackedBlock(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))
    val PETROGLYPH_RECIPE = PetroglyphRecipeBlock(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(50.0f, 1200.0f))

    //imbued deepslate
    val IMBUED_DEEPSLATE_MESSAGE = ImbuedDeepslateMessageBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
    val IMBUED_DEEPSLATE_SPLATTER = ImbuedDeepslateSplatterBlock(FabricBlockSettings.of(Material.STONE).strength(-1.0F,3600000.0f))
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
        registerBlock("aberration_spawn", ABERRATION_SPAWN, ItemGroup.MISC)
        registerBlockWithOtherItem("bull_kelp", BULL_KELP)
        registerBlockWithOtherItem("bull_kelp_plant", BULL_KELP_PLANT)
        registerBlockWithOtherItem("bull_kelp_streamer", BULL_KELP_STREAMER)
        registerBlock("calcite_sediment", CALCITE_SEDIMENT, ItemGroup.BUILDING_BLOCKS)
        registerBlock("cobbled_gabbro", COBBLED_GABBRO, ItemGroup.BUILDING_BLOCKS)
        registerBlock("crystalline_item_lock", CRYSTALLINE_ITEM_LOCK, ItemGroup.MISC)
        registerBlock("crystalline_lantern", CRYSTALLINE_LANTERN, ItemGroup.DECORATIONS)
        registerBlock("crystalline_num_lock", CRYSTALLINE_NUM_LOCK, ItemGroup.MISC)
        registerBlock("crystalline_switch", CRYSTALLINE_SWITCH, ItemGroup.MISC)
        registerBlock("crystalline_switch_pedestal", CRYSTALLINE_SWITCH_PEDESTAL, ItemGroup.MISC)
        registerBlock("facility_teleporter", FACILITY_TELEPORTER, ItemGroup.MISC)
        registerBlock("gabbro", GABBRO, ItemGroup.BUILDING_BLOCKS)
        registerBlock("hard_light_barrier", HARD_LIGHT_BARRIER, ItemGroup.BUILDING_BLOCKS)
        registerBlock("hard_light_barrier_frame", HARD_LIGHT_BARRIER_FRAME, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_message", IMBUED_DEEPSLATE_MESSAGE, ItemGroup.BUILDING_BLOCKS)
        registerBlock("imbued_deepslate_splatter", IMBUED_DEEPSLATE_SPLATTER, ItemGroup.BUILDING_BLOCKS)
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
        registerBlock("midnight_kelp", MIDNIGHT_KELP, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("midnight_kelp_plant", MIDNIGHT_KELP_PLANT)
        registerBlock("mysterious_portal_frame", MYSTERIOUS_PORTAL_FRAME, ItemGroup.BUILDING_BLOCKS)
        registerBlock("petroglyph", PETROGLYPH, ItemGroup.DECORATIONS)
        registerBlock("petroglyph_cracked", PETROGLYPH_CRACKED, ItemGroup.DECORATIONS)
        registerBlock("petroglyph_recipe", PETROGLYPH_RECIPE, ItemGroup.DECORATIONS)
        registerBlock("prismatic_amethyst", PRISMATIC_AMETHYST, ItemGroup.DECORATIONS)
        registerBlock("prismatic_gabbro", PRISMATIC_GABBRO, ItemGroup.DECORATIONS)
        registerBlock("prismatic_prismarine", PRISMATIC_PRISMARINE, ItemGroup.DECORATIONS)
        registerBlock("prismatic_quartz", PRISMATIC_QUARTZ, ItemGroup.DECORATIONS)
        registerBlock("quartzite_sand", QUARTZITE_SAND, ItemGroup.BUILDING_BLOCKS)
        registerBlockWithOtherItem("sea_apple_bush", SEA_APPLE_BUSH)
        registerBlock("sea_bamboo", SEA_BAMBOO, ItemGroup.DECORATIONS)
        registerBlockWithOtherItem("sea_bamboo_plant", SEA_BAMBOO_PLANT)
        registerBlock("turtle_grass", TURTLE_GRASS, ItemGroup.DECORATIONS)
        registerBlock("wire_weed", WIRE_WEED, ItemGroup.DECORATIONS)
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

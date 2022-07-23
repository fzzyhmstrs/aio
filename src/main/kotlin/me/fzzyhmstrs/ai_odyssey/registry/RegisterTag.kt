package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.ConfiguredStructureFeature

object RegisterTag {

    val PORTAL_FACILITY_TAG: TagKey<ConfiguredStructureFeature<*, *>> = TagKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
        Identifier(AIO.MOD_ID,"portal_facility")
    )
    val GARGANTUAN_KELP_LOGS_TAG: TagKey<Block> = TagKey.of(Registry.BLOCK_KEY,Identifier(AIO.MOD_ID,"gargantuan_kelp_logs"))
    val GARGANTUAN_SKELETAL_KELP_LOGS_TAG: TagKey<Block> = TagKey.of(Registry.BLOCK_KEY,Identifier(AIO.MOD_ID,"gargantuan_skeletal_kelp_logs"))
    val GARGANTUAN_KELP_LOGS_ITEM_TAG: TagKey<Item> = TagKey.of(Registry.ITEM_KEY,Identifier(AIO.MOD_ID,"gargantuan_kelp_logs"))
    val GARGANTUAN_SKELETAL_KELP_LOGS_ITEM_TAG: TagKey<Item> = TagKey.of(Registry.ITEM_KEY,Identifier(AIO.MOD_ID,"gargantuan_skeletal_kelp_logs"))
}
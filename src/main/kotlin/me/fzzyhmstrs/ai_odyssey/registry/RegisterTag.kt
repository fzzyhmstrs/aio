package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.ConfiguredStructureFeature

object RegisterTag {

    val PORTAL_FACILITY_TAG: TagKey<ConfiguredStructureFeature<*, *>> = TagKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
        Identifier(AIO.MOD_ID,"portal_facility")
    )
}
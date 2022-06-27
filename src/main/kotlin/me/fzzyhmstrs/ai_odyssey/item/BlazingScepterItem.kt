package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.amethyst_core.coding_util.PlayerParticles.scepterParticlePos
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentModifier
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import me.fzzyhmstrs.amethyst_core.scepter_util.base_augments.ScepterAugment
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.Identifier
import net.minecraft.world.World

class BlazingScepterItem(material: ScepterToolMaterial, settings: Settings, flavor: String = "", startingAugments: List<ScepterAugment> = listOf(), vararg defaultModifier: Identifier): CustomScepterItem(material, settings, flavor, startingAugments, *defaultModifier) {

    constructor(material: ScepterToolMaterial, settings: Settings, startingAugments: List<ScepterAugment> = listOf(), defaultModifiers: List<AugmentModifier> = listOf(), flavor: String = ""):
            this(material,settings,flavor,startingAugments,*modsToIds(defaultModifiers))

    @Environment(EnvType.CLIENT)
    override fun emitParticles(world: World, entity: PlayerEntity) {
        val client = MinecraftClient.getInstance()
        val particlePos = scepterParticlePos(client, entity)

        world.addParticle(ParticleTypes.SMOKE,particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0)
        super.emitParticles(world, entity)
    }

}
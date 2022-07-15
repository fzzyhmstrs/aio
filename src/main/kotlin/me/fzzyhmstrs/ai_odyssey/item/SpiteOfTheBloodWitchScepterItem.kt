package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.registry.RegisterParticle
import me.fzzyhmstrs.amethyst_core.coding_util.PlayerParticlesV2.scepterParticlePos
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.world.World

class SpiteOfTheBloodWitchScepterItem(material: ScepterToolMaterial, settings: Settings): CustomScepterItem(material, settings) {

    @Environment(EnvType.CLIENT)
    override fun emitParticles(world: World, client: MinecraftClient, user: LivingEntity) {
        val particlePos = scepterParticlePos(client, user)
        world.addParticle(RegisterParticle.FALLING_BLOOD,particlePos.x, particlePos.y, particlePos.z, 0.0, -0.01, 0.0)
        super.emitParticles(world,client, user)
    }

    override fun particleChance(): Int {
        return 20
    }

}
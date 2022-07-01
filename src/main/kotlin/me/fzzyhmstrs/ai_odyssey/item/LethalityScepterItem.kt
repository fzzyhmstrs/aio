package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.amethyst_core.coding_util.PlayerParticles.scepterParticlePos
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.particle.DustParticleEffect
import net.minecraft.world.World

class LethalityScepterItem(material: ScepterToolMaterial, settings: Settings): CustomScepterItem(material, settings) {

    @Environment(EnvType.CLIENT)
    override fun emitParticles(world: World, client: MinecraftClient, user: LivingEntity) {
        val particlePos = scepterParticlePos(client, user)
        val rnd1 = world.random.nextDouble() * 0.1 - 0.05
        val rnd2 = world.random.nextDouble() * 0.2 - 0.1

        world.addParticle(DustParticleEffect.DEFAULT,particlePos.x + rnd1, particlePos.y + rnd2, particlePos.z + rnd2, 0.0, 0.0, 0.0)
        super.emitParticles(world,client, user)
    }

    override fun particleChance(): Int {
        return 6
    }

}
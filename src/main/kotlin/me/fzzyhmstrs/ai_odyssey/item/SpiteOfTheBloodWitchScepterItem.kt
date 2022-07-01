package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.registry.RegisterParticle
import me.fzzyhmstrs.amethyst_core.coding_util.PlayerParticles.playerParticlePos
import me.fzzyhmstrs.amethyst_core.coding_util.PlayerParticles.scepterOffset
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.Perspective
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class SpiteOfTheBloodWitchScepterItem(material: ScepterToolMaterial, settings: Settings): CustomScepterItem(material, settings) {

    @Environment(EnvType.CLIENT)
    override fun emitParticles(world: World, client: MinecraftClient, user: LivingEntity) {
        val pos = user.getCameraPosVec(client.tickDelta)
        val width = user.width
        val perspective = client.options.perspective
        val yaw = if(perspective == Perspective.THIRD_PERSON_FRONT){
            user.bodyYaw
        } else {
            user.getYaw(client.tickDelta)
        }
        val fov = MathHelper.clamp(client.options.fov,30.0,110.0)

        val rndX = world.random.nextDouble() * 0.3 - 0.15
        val rndY = world.random.nextDouble() * 0.2 - 0.2
        val offset = scepterOffset(perspective, fov).add(rndX, rndY, 0.0)

        val particlePos = playerParticlePos(pos, width, yaw, offset)

        world.addParticle(RegisterParticle.DRIPPING_BLOOD,particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0)
        super.emitParticles(world,client, user)
    }

    override fun particleChance(): Int {
        return 10
    }

}
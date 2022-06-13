package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.registry.RegisterParticle
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentModifier
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.ScepterAugment
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.Perspective
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ToolMaterial
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class SpiteOfTheBloodWitchScepterItem(material: ToolMaterial, settings: Settings, flavor: String = "", startingAugments: List<ScepterAugment> = listOf(), vararg defaultModifier: Identifier): CustomScepterItem(material, settings, flavor, startingAugments, *defaultModifier) {

    constructor(material: ToolMaterial, settings: Settings, startingAugments: List<ScepterAugment> = listOf(), defaultModifiers: List<AugmentModifier> = listOf(), flavor: String = ""):
            this(material,settings,flavor,startingAugments,*modsToIds(defaultModifiers))

    @Environment(EnvType.CLIENT)
    override fun emitParticles(world: World, entity: PlayerEntity) {
        val client = MinecraftClient.getInstance()
        val pos = entity.getCameraPosVec(client.tickDelta)
        val width = entity.width
        val perspective = client.options.perspective
        val yaw = if(perspective == Perspective.THIRD_PERSON_FRONT){
            entity.bodyYaw
        } else {
            entity.getYaw(client.tickDelta)
        }
        val fov = MathHelper.clamp(client.options.fov,30.0,110.0)

        val rndX = world.random.nextDouble() * 0.3 - 0.15
        val rndY = world.random.nextDouble() * 0.2 - 0.2
        val offset = scepterOffset(perspective, fov).add(rndX, rndY, 0.0)

        val particlePos = playerParticlePos(pos, width, yaw, offset)

        world.addParticle(RegisterParticle.DRIPPING_BLOOD,particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0)
        super.emitParticles(world, entity)
    }

}
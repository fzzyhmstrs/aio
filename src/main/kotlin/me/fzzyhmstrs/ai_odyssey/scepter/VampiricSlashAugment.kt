package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_imbuement.scepter.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.scepter.SpectralSlashAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentEffect
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Items
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleTypes
import net.minecraft.world.World

class VampiricSlashAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SpectralSlashAugment(tier, maxLvl, *slot) {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDamage(2.5F,1.5F,0.0F).withAmplifier(0,1,0)

    override fun secondaryEffect(world: World, user: LivingEntity, target: Entity, level: Int, effect: AugmentEffect) {
        user.heal(0.25F * effect.amplifier(level))
    }

    override fun particleType(): DefaultParticleType {
        return ParticleTypes.ASH
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.FURY,18,6,9,imbueLevel, LoreTier.LOW_TIER, Items.IRON_SWORD)
    }
}
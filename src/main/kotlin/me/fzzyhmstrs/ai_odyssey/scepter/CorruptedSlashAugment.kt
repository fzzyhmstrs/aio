package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.base_augments.SlashAugment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Items
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleTypes

class CorruptedSlashAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SlashAugment(tier, maxLvl, *slot) {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDamage(3.5F,1.5F,0.0F)
            .withDuration(60,20).withAmplifier(0,0,0)

    override fun addStatusInstance(effect: AugmentEffect, level: Int): StatusEffectInstance {
        return StatusEffectInstance(StatusEffects.WITHER,effect.duration(level), effect.amplifier(level))
    }

    override fun particleType(): DefaultParticleType {
        return ParticleTypes.SMOKE
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.FURY,18,6,9,imbueLevel, LoreTier.LOW_TIER, Items.IRON_SWORD)
    }
}
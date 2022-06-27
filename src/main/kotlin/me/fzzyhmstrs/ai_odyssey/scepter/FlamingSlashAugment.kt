package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.FireAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.base_augments.SlashAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.SpectralSlashAugment
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Items
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleTypes
import net.minecraft.world.World

class FlamingSlashAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SlashAugment(tier, maxLvl, *slot),
    FireAugment {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDamage(2.5F,1.5F,0.0F)
            .withDuration(100,20)

    override fun secondaryEffect(world: World, user: LivingEntity, target: Entity, level: Int, effect: AugmentEffect) {
        target.setOnFireFor(effect.duration(level)/60)
    }

    override fun particleType(): DefaultParticleType {
        return ParticleTypes.FLAME
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.FURY,18,6,9,imbueLevel, LoreTier.LOW_TIER, Items.IRON_SWORD)
    }
}
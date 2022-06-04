package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.BaseAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.*
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.world.World

class GreaterRegenerateAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier, maxLvl, *slot), HealerAugment {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDuration(0,300).withAmplifier(3,0,0)

    override fun supportEffect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        effects: AugmentEffect
    ): Boolean {
        if(target != null) {
            if (target is PassiveEntity || target is GolemEntity || target is PlayerEntity) {
                BaseAugment.addStatusToQueue(target as LivingEntity,
                    StatusEffects.REGENERATION, effects.duration(level), effects.amplifier(level))
                world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.2F)
                effects.accept(target,AugmentConsumer.Type.BENEFICIAL)
                return true
            }
        }
        return if (user.isPlayer) {
            BaseAugment.addStatusToQueue(user, StatusEffects.REGENERATION, effects.duration(level), effects.amplifier(level))
            world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.2F)
            effects.accept(user,AugmentConsumer.Type.BENEFICIAL)
            true
        } else {
            false
        }
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.GRACE,800,100,15,imbueLevel,LoreTier.HIGH_TIER, Items.GHAST_TEAR)
    }
}
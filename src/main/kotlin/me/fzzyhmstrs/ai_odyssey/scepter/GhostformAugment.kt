package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_imbuement.scepter.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentEffect
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.MinorSupportAugment
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class GhostformAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier,maxLvl, *slot) {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDuration(180,60).withAmplifier(0,1,0)

    override fun supportEffect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        effects: AugmentEffect
    ): Boolean {
        if(target != null) {
            if (target is PassiveEntity || target is GolemEntity || target is PlayerEntity) {
                (target as LivingEntity).addStatusEffect(StatusEffectInstance(StatusEffects.INVISIBILITY, effects.duration(level)))
                target.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, effects.duration(level), effects.amplifier(level)))
                world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.0F)
                return true
            }
        }
        return if (user is PlayerEntity) {
            (target as LivingEntity).addStatusEffect(StatusEffectInstance(StatusEffects.INVISIBILITY, effects.duration(level)))
            target.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, effects.duration(level), effects.amplifier(level)))
            world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.0F)
            true
        } else {
            false
        }
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.GRACE,1200,25,1,imbueLevel, LoreTier.LOW_TIER, Items.FEATHER)
    }
}
package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.MinorSupportAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.SoulAugment
import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class GhostformAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier,maxLvl, *slot),
    SoulAugment {

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
                EffectQueue.addStatusToQueue(target as LivingEntity,StatusEffects.INVISIBILITY, effects.duration(level),0)
                EffectQueue.addStatusToQueue(target,StatusEffects.SPEED, effects.duration(level), effects.amplifier(level))
                effects.accept(target, AugmentConsumer.Type.BENEFICIAL)
                world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.0F)
                return true
            }
        }
        return if (user is PlayerEntity) {
            EffectQueue.addStatusToQueue(user,StatusEffects.INVISIBILITY, effects.duration(level),0)
            EffectQueue.addStatusToQueue(user,StatusEffects.SPEED, effects.duration(level), effects.amplifier(level))
            effects.accept(user,AugmentConsumer.Type.BENEFICIAL)
            world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.0F)
            true
        } else {
            false
        }
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.WIT,1200,25,1,imbueLevel, LoreTier.LOW_TIER, Items.FEATHER)
    }
}
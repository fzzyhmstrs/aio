package me.fzzyhmstrs.ai_odyssey.modifier

import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentConsumer
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.server.world.ServerWorld

object ModifierConsumers {

    val NECROTIC_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> necroticConsumer(list)}, AugmentConsumer.Type.HARMFUL)
    private fun necroticConsumer(list: List<LivingEntity>){
        list.forEach {
            it.addStatusEffect(
                StatusEffectInstance(StatusEffects.WITHER,80))
        }
    }
    val HEALING_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> healingConsumer(list)}, AugmentConsumer.Type.BENEFICIAL)
    private fun healingConsumer(list: List<LivingEntity>){
        list.forEach {
            it.heal(0.5F)
        }
    }
    val SMITING_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> smitingConsumer(list)}, AugmentConsumer.Type.HARMFUL)
    private fun smitingConsumer(list: List<LivingEntity>){
        list.forEach {
            if(it.isUndead){
                if (!it.isDead) {
                    it.isInvulnerable = false //these two lines take away damage invulnerability
                    it.timeUntilRegen = 0
                    it.damage(DamageSource.GENERIC, 2.0F)
                }
            }
        }
    }
    val INSIGHTFUL_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> insightfulConsumer(list) }, AugmentConsumer.Type.HARMFUL)
    private fun insightfulConsumer(list: List<LivingEntity>){
        list.forEach {
            if (!it.isAlive){
                val world = it.world
                if (world is ServerWorld) {
                    when (it) {
                        is PassiveEntity -> {
                            ExperienceOrbEntity.spawn(world, it.pos, 1)
                        }
                        is Angerable -> {
                            ExperienceOrbEntity.spawn(world, it.pos, 2)
                        }
                        else -> {
                            ExperienceOrbEntity.spawn(world, it.pos, 3)
                        }
                    }
                }
            }
        }
    }

}
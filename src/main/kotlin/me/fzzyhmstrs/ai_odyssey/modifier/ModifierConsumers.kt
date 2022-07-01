package me.fzzyhmstrs.ai_odyssey.modifier

import me.fzzyhmstrs.amethyst_core.item_util.AugmentScepterItem
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterHelper
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentHelper
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.ScepterAugment
import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterStatus
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModifierConsumers {

    val NECROTIC_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> necroticConsumer(list)}, AugmentConsumer.Type.HARMFUL)
    private fun necroticConsumer(list: List<LivingEntity>){
        list.forEach {
            if (it.hasStatusEffect(StatusEffects.WITHER)){
                val effect = it.getStatusEffect(StatusEffects.WITHER)
                val amp = effect?.amplifier?:0
                val duration = effect?.duration?:0
                if (duration > 0){
                    val duration2 = if(duration < 80) {80} else {duration}
                    it.addStatusEffect(StatusEffectInstance(StatusEffects.WITHER,duration2,amp + 1))
                }
            } else {
                it.addStatusEffect(
                    StatusEffectInstance(StatusEffects.WITHER, 80)
                )
            }
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

    val ECHOING_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> echoingConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun echoingConsumer(list: List<LivingEntity>){
        val user = list[0]
        val stack = user.getStackInHand(Hand.MAIN_HAND)
        val item = stack.item
        if (item is AugmentScepterItem){
            val activeEnchant = item.getActiveEnchant(stack)
            val augment = Registry.ENCHANTMENT.get(Identifier(activeEnchant))
            if (augment != null && augment is ScepterAugment){
                augment.applyModifiableTasks(user.world,user,Hand.MAIN_HAND,1)
            }
        }
    }
    val OCEANIC_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> oceanicConsumer(list)}, AugmentConsumer.Type.BENEFICIAL)
    private fun oceanicConsumer(list: List<LivingEntity>){
        list.forEach {
            EffectQueue.addStatusToQueue(it,StatusEffects.DOLPHINS_GRACE,14,0)
        }
    }

    val PROTECTIVE_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> protectiveConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun protectiveConsumer(list: List<LivingEntity>){
        list.forEach {
            EffectQueue.addStatusToQueue(it,RegisterStatus.SHIELDING,20,0)
        }
    }

    val BLOOD_PACT_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> bloodPactConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun bloodPactConsumer(list: List<LivingEntity>){
        list.forEach {
            if (it.isPlayer){
                it.damage(DamageSource.GENERIC,0.6666666F)
            }
        }
    }
    
    val KNOWLEDGE_PACT_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> knowledgePactConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun knowledgePactConsumer(list: List<LivingEntity>){
        list.forEach {
            if (it is PlayerEntity){
                it.applyEnchantmentCosts(it.getStackInHand(Hand.MAIN_HAND), 1)
            }
        }
    }

    val TRAVELER_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> travelerConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun travelerConsumer(list: List<LivingEntity>){
        list.forEach {
            if (it is PlayerEntity){
                val rnd1 = it.world.random.nextInt(8)
                if (rnd1 == 0)
                EffectQueue.addStatusToQueue(it,StatusEffects.SPEED,30,0)
            }
        }
    }

}

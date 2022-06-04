package me.fzzyhmstrs.ai_odyssey.modifier

import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.BaseAugment
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentConsumer
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.ScepterAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import me.fzzyhmstrs.amethyst_imbuement.util.NbtKeys
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

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

    val ECHOING_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> echoingConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun echoingConsumer(list: List<LivingEntity>){
        val user = list[0]
        val stack = user.getStackInHand(Hand.MAIN_HAND)
        val item = stack.item
        if (item is ScepterItem){
            val activeEnchant = ScepterObject.activeEnchantHelper(user.world,stack, Nbt.readStringNbt(NbtKeys.ACTIVE_ENCHANT.str(), stack.orCreateNbt))
            val augment = Registry.ENCHANTMENT.get(Identifier(activeEnchant))
            if (augment != null && augment is ScepterAugment){
                augment.applyModifiableTasks(user.world,user,Hand.MAIN_HAND,1)
            }
        }
    }
    val OCEANIC_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> oceanicConsumer(list)}, AugmentConsumer.Type.BENEFICIAL)
    private fun oceanicConsumer(list: List<LivingEntity>){
        list.forEach {
            BaseAugment.addStatusToQueue(it,StatusEffects.DOLPHINS_GRACE,14,0)
        }
    }

    val PROTECTIVE_CONSUMER = AugmentConsumer({ list: List<LivingEntity> -> protectiveConsumer(list) }, AugmentConsumer.Type.BENEFICIAL)
    private fun protectiveConsumer(list: List<LivingEntity>){
        list.forEach {
            BaseAugment.addStatusToQueue(it,RegisterStatus.SHIELDING,20,0)
        }
    }

}
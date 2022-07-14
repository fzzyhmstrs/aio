package me.fzzyhmstrs.ai_odyssey.augment

import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.TotemPassiveAugment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack

class EnragedAugment(weight: Rarity, mxLvl: Int = 1, vararg slot: EquipmentSlot): TotemPassiveAugment(weight,mxLvl, *slot) {

    private val enragedVsHealth: Map<Float, Int> = mapOf(0.6F to 0,0.4F to 1, 0.2F to 3)

    override fun tickEffect(user: LivingEntity, level: Int, stack: ItemStack) {
        val healthPercent = user.health/user.maxHealth
        var enragedLevel = -1
        enragedVsHealth.forEach {
            if (healthPercent <= it.key){
                enragedLevel = it.value
            }
        }
        if (enragedLevel > -1){
            EffectQueue.addStatusToQueue(user,StatusEffects.STRENGTH,40,enragedLevel)
        }
        if (enragedLevel == 3){
            EffectQueue.addStatusToQueue(user,StatusEffects.RESISTANCE,40,0)
        }
    }

}
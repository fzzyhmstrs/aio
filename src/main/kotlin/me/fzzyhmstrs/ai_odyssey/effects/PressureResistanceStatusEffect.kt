package me.fzzyhmstrs.ai_odyssey.effects

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory

class PressureResistanceStatusEffect(statusEffectCategory: StatusEffectCategory, i: Int): StatusEffect(statusEffectCategory, i) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return false
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
    }
}
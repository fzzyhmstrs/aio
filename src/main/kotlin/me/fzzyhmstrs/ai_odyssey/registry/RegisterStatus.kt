package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.effects.DeterminationStatusEffect
import me.fzzyhmstrs.ai_odyssey.effects.PressureResistanceStatusEffect
import me.fzzyhmstrs.ai_odyssey.effects.ResonatingStatusEffect
import me.fzzyhmstrs.ai_odyssey.effects.UndeathStatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterStatus {

    val DETERMINATION = DeterminationStatusEffect(StatusEffectCategory.BENEFICIAL,0x000000)
    val PRESSURE_RESISTANCE = PressureResistanceStatusEffect(StatusEffectCategory.BENEFICIAL,0x000000)
    val RESONATING = ResonatingStatusEffect(StatusEffectCategory.HARMFUL,0x39D6E0)
    val UNDEATH = UndeathStatusEffect(StatusEffectCategory.BENEFICIAL,0x000000)

    fun registerAll(){
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"determination"), DETERMINATION)
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"pressure_resistance"), PRESSURE_RESISTANCE)
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"resonating"), RESONATING)
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"undeath"), UNDEATH)
    }
}
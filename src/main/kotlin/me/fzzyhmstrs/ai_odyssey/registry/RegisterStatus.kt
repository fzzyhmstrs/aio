package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.effects.DeterminationStatusEffect
import me.fzzyhmstrs.ai_odyssey.effects.ResonatingStatusEffect
import me.fzzyhmstrs.ai_odyssey.effects.UndeathStatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterStatus {

    val UNDEATH = UndeathStatusEffect(StatusEffectCategory.BENEFICIAL,0x000000)
    val DETERMINATION = DeterminationStatusEffect(StatusEffectCategory.BENEFICIAL,0x000000)
    val RESONATING = ResonatingStatusEffect(StatusEffectCategory.HARMFUL,0xFFFFFF)

    fun registerAll(){
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"undeath"), UNDEATH)
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"determination"), DETERMINATION)
        Registry.register(Registry.STATUS_EFFECT, Identifier(AIO.MOD_ID,"resonating"), RESONATING)
    }
}
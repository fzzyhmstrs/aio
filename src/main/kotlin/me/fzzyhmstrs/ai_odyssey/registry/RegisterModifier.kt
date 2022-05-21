package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterModifier
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentModifier

object RegisterModifier {

    private val regMod: MutableList<AugmentModifier> = mutableListOf()


    fun registerAll(){
        regMod.forEach {
            RegisterModifier.ENTRIES.register(it)
        }
        regMod.clear()
    }

}
package me.fzzyhmstrs.ai_odyssey

import me.fzzyhmstrs.ai_odyssey.config.AioConfig
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.ai_odyssey.registry.RegisterModifier
import net.fabricmc.api.ModInitializer

object AIO: ModInitializer {
    const val MOD_ID = "ai_odyssey"

    override fun onInitialize() {
        AioConfig.initConfig()
        RegisterItem.registerAll()
        RegisterModifier.registerAll()
    }
}
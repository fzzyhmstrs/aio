package me.fzzyhmstrs.ai_odyssey

import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.fabricmc.api.ModInitializer

object AIO: ModInitializer {
    const val MOD_ID = "ai_odyssey"

    override fun onInitialize() {
        RegisterItem.registerAll()
    }
}
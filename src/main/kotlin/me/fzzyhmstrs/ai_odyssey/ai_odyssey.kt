package me.fzzyhmstrs.ai_odyssey

import me.fzzyhmstrs.ai_odyssey.config.AioConfig
import me.fzzyhmstrs.ai_odyssey.registry.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

object AIO: ModInitializer {
    const val MOD_ID = "ai_odyssey"

    override fun onInitialize() {
        AioConfig.initConfig()
        RegisterArmor.registerAll()
        RegisterItem.registerAll()
        RegisterBlock.registerAll()
        RegisterEnchantment.registerAll()
        RegisterEntity.registerAll()
        RegisterStatus.registerAll()
        RegisterModifier.registerAll()
        RegisterParticle.registerParticleTypes()
    }
}

object AIO_Client: ClientModInitializer{

    override fun onInitializeClient() {
        RegisterRenderer.registerAll()
        RegisterItemModel.registerAll()

    }

}
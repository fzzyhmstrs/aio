package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.model.LambentTridentEntityModel
import me.fzzyhmstrs.ai_odyssey.model.LambentTridentItemEntityRenderer
import me.fzzyhmstrs.amethyst_core.registry.ItemModelRegistry
import net.minecraft.client.util.ModelIdentifier

object RegisterItemModel {

    fun registerAll(){
        val blazingScepterModels = ItemModelRegistry.ModelIdentifierPerModes(ModelIdentifier(AIO.MOD_ID + ":blazing_scepter#inventory"))
            .withHeld(ModelIdentifier(AIO.MOD_ID + ":blazing_scepter_in_hand#inventory"), true)
        ItemModelRegistry.registerItemModelId(RegisterItem.BLAZING_SCEPTER, blazingScepterModels)
        val lambentTridentModels = ItemModelRegistry.ModelIdentifierPerModes(ModelIdentifier(AIO.MOD_ID + ":lambent_trident#inventory"))
            .withHeld(ModelIdentifier(AIO.MOD_ID + ":lambent_trident_in_hand#inventory"), true)
        ItemModelRegistry.registerItemModelId(RegisterItem.LAMBENT_TRIDENT, lambentTridentModels)
        ItemModelRegistry.registerItemEntityModel(
            RegisterItem.LAMBENT_TRIDENT,
            LambentTridentItemEntityRenderer,
            RegisterRenderer.LAMBENT_TRIDENT,
            LambentTridentEntityModel::class.java)
    }
}
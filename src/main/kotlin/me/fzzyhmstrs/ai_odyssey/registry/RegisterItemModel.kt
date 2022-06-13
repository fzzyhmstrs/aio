package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterItemModel
import net.minecraft.client.util.ModelIdentifier

object RegisterItemModel {

    fun registerAll(){
        val modelsPerMode = RegisterItemModel.ModelIdentifierPerModes(ModelIdentifier(AIO.MOD_ID + ":blazing_scepter#inventory"))
            .withHeld(ModelIdentifier(AIO.MOD_ID + ":blazing_scepter_in_hand#inventory"), true)
        RegisterItemModel.registerItemModelId(RegisterItem.BLAZING_SCEPTER, modelsPerMode)
    }
}
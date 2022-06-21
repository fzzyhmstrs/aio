package me.fzzyhmstrs.ai_odyssey.registry


import me.fzzyhmstrs.ai_odyssey.screen.*
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

object RegisterScreen {

    fun registerAll(){
        HandledScreens.register(RegisterHandler.IMBUED_MESSAGE_SCREEN_HANDLER) {
                handler: ImbuedMessageScreenHandler, playerInventory: PlayerInventory, title: Text ->
            ImbuedMessageScreen(
                handler,
                playerInventory,
                title
            )
        }
        HandledScreens.register(RegisterHandler.IMBUED_SPLATTER_SCREEN_HANDLER) {
                handler: ImbuedSplatterScreenHandler, playerInventory: PlayerInventory, title: Text ->
            ImbuedSplatterScreen(
                handler,
                playerInventory,
                title
            )
        }
        HandledScreens.register(RegisterHandler.PETROGLYPH_RECIPE_SCREEN_HANDLER) {
                handler: PetroglyphRecipeScreenHandler, playerInventory: PlayerInventory, title: Text ->
            PetroglyphRecipeScreen(
                handler,
                playerInventory,
                title
            )
        }
    }

}

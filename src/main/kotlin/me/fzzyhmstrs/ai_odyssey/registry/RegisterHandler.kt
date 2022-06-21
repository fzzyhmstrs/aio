package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.screen.ImbuedMessageScreenHandler
import me.fzzyhmstrs.ai_odyssey.screen.ImbuedSplatterScreenHandler
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerType

object RegisterHandler {

    var IMBUED_SPLATTER_SCREEN_HANDLER: ScreenHandlerType<ImbuedSplatterScreenHandler>? = null
    var IMBUED_MESSAGE_SCREEN_HANDLER: ScreenHandlerType<ImbuedMessageScreenHandler>? = null

    fun registerAll(){
        IMBUED_SPLATTER_SCREEN_HANDLER = ScreenHandlerType { syncID: Int, playerInventory: PlayerInventory ->
            ImbuedSplatterScreenHandler(
                syncID,
                playerInventory
            )
        }
        IMBUED_MESSAGE_SCREEN_HANDLER = ScreenHandlerType { syncID: Int, playerInventory: PlayerInventory ->
            ImbuedMessageScreenHandler(
                syncID,
                playerInventory
            )
        }
        PETROGLYPH_RECIPE_SCREEN_HANDLER = ScreenHandlerType { syncID: Int, playerInventory: PlayerInventory ->
            PetroglyphRecipeScreenHandler(
                syncID,
                playerInventory
            )
        }
    }

}

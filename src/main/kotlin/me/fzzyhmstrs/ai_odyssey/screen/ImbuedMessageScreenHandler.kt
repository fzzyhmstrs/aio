package me.fzzyhmstrs.ai_odyssey.screen

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext

class ImbuedMessageScreenHandler(
    syncID: Int,
    playerInventory: PlayerInventory,
    private val context: ScreenHandlerContext
): ScreenHandler(RegisterHandler.IMBUED_MESSAGE_SCREEN_HANDLER, syncID) {

    constructor(syncID: Int, playerInventory: PlayerInventory) : this(
        syncID,
        playerInventory,
        ScreenHandlerContext.EMPTY
    )

    override fun canUse(player: PlayerEntity?): Boolean {
        return canUse(this.context, player, RegisterBlock.IMBUED_DEEPSLATE_MESSAGE)
    }

    override fun onButtonClick(player: PlayerEntity?, id: Int): Boolean {
        return super.onButtonClick(player, id)
    }
}
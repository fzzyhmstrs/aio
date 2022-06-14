package me.fzzyhmstrs.ai_odyssey.screen

import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class ImbuedMessageScreen(handler: ImbuedMessageScreenHandler, playerInventory: PlayerInventory, title: Text):
    HandledScreen<ImbuedMessageScreenHandler>(handler, playerInventory, title) {

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        TODO("Not yet implemented")
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)
    }
}
package me.fzzyhmstrs.ai_odyssey.screen

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.block.ImbuedDeepslateMessageBlock
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ImbuedMessageScreen(handler: ImbuedMessageScreenHandler, playerInventory: PlayerInventory, title: Text):
    HandledScreen<ImbuedMessageScreenHandler>(handler, playerInventory, title) {

    private val texture = Identifier(AIO.MOD_ID,"textures/gui/message_screen_gui.png")
    private val player = playerInventory.player
    private val xOffset = (width - backgroundWidth)/2
    private val yOffset = (height - backgroundHeight)/2

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        val indexes = MessageScreenHelper.calcIndexes(mouseX, mouseY, xOffset, yOffset)
        if (indexes != null){
            val id = MessageScreenHelper.linearIndexFromIndexes(indexes)
            return if (!handler.onButtonClick(player,id) ){
                super.mouseClicked(mouseX, mouseY, button)
            } else {
                client?.interactionManager?.clickButton(handler.syncId, id)
                true
            }
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        MessageScreenHelper.backgroundInitialize(this.texture)
        this.drawTexture(matrices, xOffset, yOffset, 0, 0, backgroundWidth, backgroundHeight)
        MessageScreenHelper.backgroundViewport(height, width, client)
        MessageScreenHelper.backgroundReinitialize(this.texture)
        val indexes = MessageScreenHelper.calcIndexes(mouseX.toDouble(), mouseY.toDouble(), xOffset, yOffset)
        if (indexes != null){
            val coordinates = MessageScreenHelper.coordinatesFromIndexes(indexes, xOffset, yOffset)
            this.drawTexture(matrices, coordinates.first, coordinates.second, 0,166,32,32)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        val indexes = MessageScreenHelper.calcIndexes(mouseX.toDouble(), mouseY.toDouble(), xOffset, yOffset)
        if (indexes != null){
            val id = MessageScreenHelper.linearIndexFromIndexes(indexes)
            if (ImbuedDeepslateMessageBlock.messageMap.containsKey(id)){
                val tooltipText = LiteralText(ImbuedDeepslateMessageBlock.messageMap[id]?.name?:"")
                this.renderTooltip(matrices, tooltipText, mouseX, mouseY)
            }
        }
    }
}
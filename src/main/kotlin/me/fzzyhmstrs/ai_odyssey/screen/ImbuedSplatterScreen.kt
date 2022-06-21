package me.fzzyhmstrs.ai_odyssey.screen

import com.mojang.blaze3d.systems.RenderSystem
import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.block.ImbuedDeepslateSplatterBlock
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.Matrix4f

class ImbuedSplatterScreen(handler: ImbuedSplatterScreenHandler, playerInventory: PlayerInventory, title: Text):
    HandledScreen<ImbuedSplatterScreenHandler>(handler, playerInventory, title) {

    private val texture = Identifier(AIO.MOD_ID,"textures/gui/splatter_screen_gui.png")
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
            if (ImbuedDeepslateSplatterBlock.splatterMap.containsKey(id)){
                val tooltipText = LiteralText(ImbuedDeepslateSplatterBlock.splatterMap[id]?.name?:"")
                this.renderTooltip(matrices, tooltipText, mouseX, mouseY)
            }
        }
    }

    init {
        super.init()
    }
}
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

    private val texture = Identifier(AIO.MOD_ID,"textures/gui/container/splatter_screen_gui.png")
    private val player = playerInventory.player

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        val indexes = MessageScreenHelper.calcIndexes(mouseX, mouseY, i, j)
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
        DiffuseLighting.disableGuiDepthLighting()
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, this.texture)
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        this.drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)
        val k = client?.window?.scaleFactor?.toInt()?:1
        RenderSystem.viewport((width - 320) / 2 * k, (height - 240) / 2 * k, 320 * k, 240 * k)
        val matrix4f = Matrix4f.translate(-0.34f, 0.23f, 0.0f)
        matrix4f.multiply(Matrix4f.viewboxMatrix(90.0, 1.3333334f, 9.0f, 80.0f))
        RenderSystem.backupProjectionMatrix()
        RenderSystem.setProjectionMatrix(matrix4f)

        client?.window?.framebufferWidth?.let { client?.window?.framebufferHeight?.let { it1 ->
            RenderSystem.viewport(0, 0, it,
                it1
            )
        } }

        RenderSystem.restoreProjectionMatrix()
        DiffuseLighting.enableGuiDepthLighting()
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderTexture(0, this.texture)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        val indexes = MessageScreenHelper.calcIndexes(mouseX.toDouble(), mouseY.toDouble(), i, j)
        if (indexes != null){
            val coordinates = MessageScreenHelper.coordinatesFromIndexes(indexes, i, j)
            this.drawTexture(matrices, coordinates.first, coordinates.second, 0,166,32,32)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        val indexes = MessageScreenHelper.calcIndexes(mouseX.toDouble(), mouseY.toDouble(), i, j)
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
package me.fzzyhmstrs.ai_odyssey.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.sun.jna.platform.win32.Wincon
import me.fzzyhmstrs.ai_odyssey.block.ImbuedDeepslateMessageBlock
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.client.render.GameRenderer
import net.minecraft.util.Identifier
import net.minecraft.util.math.Matrix4f
import kotlin.math.min

object MessageScreenHelper {

    val xCoordinates: Map<Int,Int> = mapOf(
        0 to 8,
        1 to 40,
        2 to 72,
        3 to 104,
        4 to 136
    )
    val yCoordinates: Map<Int,Int> = mapOf(
        0 to 3,
        1 to 35,
        2 to 67,
        3 to 99,
        4 to 131
    )
    private const val xOffset = 8
    private const val yOffset = 3
    private const val width = 160
    val widthPerCell = width / xCoordinates.size
    private const val height = 160
    val heightPerCell = height / yCoordinates.size
    val maxIndexX = xCoordinates.size - 1
    val maxIndexY = yCoordinates.size - 1

    fun calcIndexes(mouseX: Double, mouseY: Double, widthOffset: Int, heightOffset: Int): Pair<Int, Int>?{
        val d = mouseX - (widthOffset + xOffset)
        if (d < 0 || d >= width) return null
        val e = mouseY - (heightOffset + yOffset)
        if (e < 0 || e >= height) return null
        return Pair((width/d).toInt(),(height/e).toInt())
    }

    fun coordinatesFromIndexes(indexes: Pair<Int,Int>, widthOffset: Int, heightOffset: Int): Pair<Int, Int>{
        val xInd = min(indexes.first, maxIndexX)
        val yInd = min(indexes.second, maxIndexY)
        return Pair((xCoordinates[xInd]?:0) + widthOffset + xOffset, (yCoordinates[yInd]?:0) + heightOffset + yOffset)
    }

    fun linearIndexFromIndexes(indexes: Pair<Int, Int>): Int{
        return indexes.first + indexes.second * 10
    }

    fun backgroundInitialize(texture: Identifier){
        DiffuseLighting.disableGuiDepthLighting()
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, texture)
    }
    fun backgroundViewport(height: Int, width: Int, client: MinecraftClient?){
        val k = client?.window?.scaleFactor?.toInt()?:1
        RenderSystem.viewport((width - 320) / 2 * k, (height - 240) / 2 * k, 320 * k, 240 * k)
        backgroundSetMatrix()
        client?.window?.framebufferWidth?.let { client.window?.framebufferHeight?.let { it1 ->
            RenderSystem.viewport(0, 0, it,
                it1
            )
        } }
    }
    private fun backgroundSetMatrix(){
        val matrix4f = Matrix4f.translate(-0.34f, 0.23f, 0.0f)
        matrix4f.multiply(Matrix4f.viewboxMatrix(90.0, 1.3333334f, 9.0f, 80.0f))
        RenderSystem.backupProjectionMatrix()
        RenderSystem.setProjectionMatrix(matrix4f)
    }
    fun backgroundReinitialize(texture: Identifier){
        RenderSystem.restoreProjectionMatrix()
        DiffuseLighting.enableGuiDepthLighting()
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

    fun <T: IndexedEnum> enumToIndexes(values: Array<T>): Map<Int, T>{
        val map: MutableMap<Int, T> = mutableMapOf()

        values.forEach {
            val index = it.coordinatesToIndex()
            map[index] = it
        }
        return map
    }

    interface IndexedEnum{
        fun coordinatesToIndex(): Int
    }

}
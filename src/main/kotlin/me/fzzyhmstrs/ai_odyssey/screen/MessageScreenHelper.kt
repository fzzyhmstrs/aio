package me.fzzyhmstrs.ai_odyssey.screen

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

}
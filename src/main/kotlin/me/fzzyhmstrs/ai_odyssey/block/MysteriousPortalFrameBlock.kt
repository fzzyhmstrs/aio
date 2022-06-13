package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.abs

class MysteriousPortalFrameBlock(settings: Settings): Block(settings), SwitchDoor {
    
    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY)
        if (entity == null) {
            user?.sendMessage(TranslatableText("message.configurator.entity_fail",pos.toString()), false)
            return ActionResult.FAIL
        }
        val layerMapResult = composePortalFrame(world, pos)
        if (!layerMapResult.failed) {
            val layerMap = layerMapResult.layerMap
            val frameList = layersToList(layerMap)
            for (frame in frameList){
                if (frame == pos) continue
                val frameState = world.getBlockState(frame)
                if (!frameState.isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME)){
                    user?.sendMessage(TranslatableText("message.portal_frame.frame_fail",frame.toString()), false)
                    return ActionResult.FAIL
                }
                val chkEntity = RegisterEntity.getBlockEntity(world, frame, RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY)
                if (chkEntity == null) {
                    user?.sendMessage(TranslatableText("message.configurator.entity_fail",frame.toString()), false)
                    return ActionResult.FAIL
                } else if (chkEntity.isPortalKey()){
                    user?.sendMessage(TranslatableText("message.portal_frame.key_set_fail",frame.toString()), false)
                    return ActionResult.FAIL
                }
            }
            entity.setAsPortalKey(frameList)
            return ActionResult.SUCCESS
        } else {
            user?.sendMessage(layerMapResult.message, false)
            return ActionResult.FAIL
        }
    }

    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

    override fun doorType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.PORTAL
    }
    
    companion object{

        private val maxSearchOffset = 12
    
        private fun composePortalFrame(world: World, pos: BlockPos): FrameResult{
            val layerMap: MutableMap<Int, PortalLayer> = mutableMapOf()
            val axis: Direction.Axis = Direction.Axis.X
            var bl = true
            var offset = 0
            var continuousCount = 0
            while (bl){
                var bl2 = false
                var offset2 = 0
                val elevation = pos.y + offset
                val newLayer = PortalLayer(axis)
                while (bl2) {
                    val testPos1 = pos.offset(axis,offset2).add(0,offset,0)
                    val testPos2 = pos.offset(axis,-offset2).add(0,offset,0)
                    if (world.getBlockState(testPos1).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME)){
                        newLayer.addFramePos(testPos1)
                        if (newLayer.isContinuous()){
                                continuousCount++
                            }
                        if (!(newLayer.isContinuous() || newLayer.getSize() < 2)) {
                            layerMap[elevation] = newLayer
                            bl2 = false
                            continue
                        }
                    }
                    if (world.getBlockState(testPos2).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME)){
                        newLayer.addFramePos(testPos2)
                        if (!(newLayer.isContinuous() || newLayer.getSize() < 2)) {
                            if (newLayer.isContinuous()){
                                continuousCount++
                            }
                            layerMap[elevation] = newLayer
                            bl2 = false
                            continue
                        }
                    }
                    offset2++
                    if (offset2 > maxSearchOffset){
                        if (newLayer.getSize() == 0) {
                            return FrameResult(TranslatableText("message.portal_frame.too_wide", elevation.toString(), maxSearchOffset.toString()))
                        }
                    }
                }
                if (continuousCount >= 2) {
                    if (offset < 3) {
                        return FrameResult(TranslatableText("message.portal_frame.too_short", "4", (offset + 1).toString()))
                    }
                } else {
                    @Suppress("UNUSED_VALUE")
                    bl = false
                    break
                }
                offset++
                if (offset > maxSearchOffset){
                    return FrameResult(TranslatableText("message.portal_frame.too_tall", "4", (offset + 1).toString()))
                }
            }
            val sortedLayerMap = layerMap.toSortedMap()
            var bl3 = true
            val first = sortedLayerMap.firstKey()
            val last = sortedLayerMap.lastKey() - 1
            for (i in first..last){
                val entry = sortedLayerMap[i]?:continue
                val test = sortedLayerMap[i + 1]?:continue
                bl3 = bl3 && entry.checkValidity(test)
                if (!bl3){
                    return FrameResult(TranslatableText("message.portal_frame.layer_fail", i.toString(), (i + 1).toString()))
                }
            }
            return if(bl3 && sortedLayerMap[first]?.isContinuous() == true){
                FrameResult(layerMap)
            } else {
                FrameResult(TranslatableText("message.portal_frame.base_fail"))
            }
        }

        private fun layersToList(layerMap: Map<Int, PortalLayer>): List<BlockPos>{
            val list: MutableList<BlockPos> = mutableListOf()
            layerMap.forEach{
                list.addAll(it.value.getFrameList())
            }
            return list
        }


        private class PortalLayer(val axis: Direction.Axis){
            private val frameList: MutableList<BlockPos> = mutableListOf()
            private var continuous: Boolean = true

            fun addFramePos(pos: BlockPos){
                if (frameList.isEmpty()){
                    frameList.add(pos)
                } else {
                    frameList.add(pos)
                    continuous = checkContinuity()
                }
            }

            fun isContinuous(): Boolean{
                return continuous
            }
            fun getSize(): Int{
                return frameList.size
            }

            fun getFrameList(): List<BlockPos>{
                return frameList
            }

            private fun checkContinuity(): Boolean{
                val thisFrameList = this.getFrameList()
                if (thisFrameList.size <= 1) return true
                var prev = valByAxis(thisFrameList[0], axis)
                for (i in 1 until thisFrameList.size){
                    val next = valByAxis(thisFrameList[i], axis)
                    if (next - prev > 1){
                        return false
                    } else {
                        prev = next
                    }
                }
                return true
            }

            fun checkValidity(other: PortalLayer): Boolean{
                if (other.axis != axis) return false
                val thisFrameList: List<BlockPos> = this.getFrameList()
                val otherFrameList: List<BlockPos> = other.getFrameList()
                if (otherFrameList.isEmpty() || thisFrameList.isEmpty()) return false
                if (!other.isContinuous() && otherFrameList.size > 2) return false
                val first = valByAxis(thisFrameList[0], axis)
                val last = valByAxis(thisFrameList[thisFrameList.size - 1], axis)
                val otherFirst = valByAxis(otherFrameList[0], axis)
                val otherLast = valByAxis(otherFrameList[otherFrameList.size - 1], axis)
                return (abs(otherFirst - first) <= 1 && abs(otherLast - last) <= 1)
            }

            private fun valByAxis(pos: BlockPos, axis: Direction.Axis): Int{
                return when(axis){
                    Direction.Axis.X-> {
                        pos.x
                    }
                    Direction.Axis.Y-> {
                        pos.y
                    }
                    Direction.Axis.Z-> {
                        pos.z
                    }
                }
            }
        }

        private class FrameResult(val layerMap: Map<Int, PortalLayer>) {

            constructor(failMessage: Text): this(mapOf()){
                failed = true
                message = failMessage
            }
            var message: Text = LiteralText("")
            var failed = false
        }

    }
}

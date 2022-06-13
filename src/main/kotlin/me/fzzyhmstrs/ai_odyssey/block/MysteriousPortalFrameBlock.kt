package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MysteriousPortalFrameBlock(settings: Settings): Block(settings), SwitchDoor {

    private val maxSearchOffset = 12
    
    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY)
        if (entity == null) {
            user?.sendMessage("message.portal_frame.entity_fail",pos.toString(), false)
            return ActionResult.FAIL
        }
        val layerMap = composePortalFrame(world, pos)
        if (layerMap != null) {
            val frameList = layersToList(layerMap)
            for (frame in frameList){
                if (frame == pos) continue
                val state = world.getBlockState(frame)
                if (!state.isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME){
                    user?.sendMessage("message.portal_frame.frame_fail",frame.toString(), false)
                    return ActionResult.FAIL
                }
                val chkEntity = RegisterEntity.getBlockEntity(world, frame, RegisterEntity.MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY)
                if (chkEntity == null) {
                    user?.sendMessage("message.portal_frame.entity_fail",frame.toString(), false)
                    return ActionResult.FAIL
                } else if (chkEntity.isPortalKey){
                    user?.sendMessage("message.portal_frame.key_set_fail",frame.toString(), false)
                    return ActionResult.FAIL
                }
            }
            entity.setAsPortalKey(frameList)
            return ActionResult.SUCCESS
        } else {
            user?.sendMessage("message.portal_frame.frame_find_fail", false)
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
    
    private fun composePortalFrame(world: World, pos: BlockPos): Map<Int, PortalLayer>?{
        val layerMap: MutableMap<Int, PortalLayer> = mutableMapOf()
        val axis: Direction.Axis = TODO("need IDE so I can build the Axis finder")
        var bl: Boolean = true
        var offset = 0
        var continuousCount = 0
        while (bl){
            var bl2 = false
            var offset2 = 0
            val elevation = pos.y + offset
            val newLayer = PortalLayer(axis)
            while (bl2) {
                val testPos1 = TODO("need IDE")
                val testPos2 = TODO("need IDE")
                if (world.getBlockState(testPos1).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME){
                    newLayer.addFramePos(testPos1)
                    if (newLayer.isContinuous){
                            continuousCount++
                        }
                    if (!(newLayer.isContinuous || newLayer.size < 2)) {
                        layerMap[elevation] = newLayer
                        bl2 = false
                        continue
                    }
                }
                if (world.getBlockState(testPos2).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME){
                    newLayer.addFramePos(testPos2)
                    if (!(newLayer.isContinuous || newLayer.size < 2)) {
                        if (newLayer.isContinuous){
                            continuousCount++
                        }
                        layerMap[elevation] = newLayer
                        bl2 = false
                        continue
                    }
                }
                if (world.getBlockState(testPos1).isAir() && world.getBlockState(testPos2).isAir()){
                    
                }
                offset2++
                if (offset2 > maxSearchOffset){
                    return null
                }
            }
            if (continuousCount >= 2) {
                if (offset < 3) {
                    return null
                }
            } else {
                break
            }
            offset++
            if (offset > maxSearchOffset){
                return null
            }
        }
        val sortedLayerMap = layerMap.toSortedMap()
        var bl = true
        val first = sortedLayerMap.firstKey
        val last = sortedLayerMap.lastKey - 1
        for (i in first..last){
            val entry = sortedLayerMap[i]
            val test = sortedLayerMap[i + 1]
            bl = bl && entry.value.checkValidity(test)
        }
        return if(bl && sortedLayerMap[first].isContinuous){
            layerMap
        } else {
            null
        }
    }
    
    private fun layersToList(layerMap: Map<Int, PortalLayer>): List<BLockPos>{
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
            if (frameList.isEmpty(){
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
            }
            for (i in 1..(thisFrameList.size - 1)){
                val next = valByAxis(thisFrameList[i], axis)
                if (next - prev > 1){
                    return false
                } else {
                    prev = next
                }
            }
            return true
        }
            
        private fun checkValidity(other: portalLayer): Boolean{
            if (other.axis != axis) return false
            val thisFrameList: List<BlockPos> = this.getFrameList()
            val otherFrameList: List<BlockPos> = other.getFrameList
            if (otherFrameList.isEmpty() || thisFrameList.isEmpty) return false
            if (!other.isContinuous && otherFrameList.size > 2) return false
            val first = valByAxis(thisFrameList[0], axis)
            val last = valByAxis(thisFrameList[thisFrameList.size - 1], axis)
            val otherFirst = valByAxis(otherFrameList[0], axis)
            val otherLast = valByAxis(otherFrameList[otherFrameList.size - 1], axis)
            return (abs(otherFirst - first) <= 1 && abs(otherLast - last) <= 1)
            
        }
        private fun valByAxis(pos: BlockPos, axis: Direction.Axis): Int{
            return when(axis){
                EAST_WEST-> {
                    pos.x
                }
                NORTH_SOUTH-> {
                    pos.Z
                }
        }
    }
    }
}

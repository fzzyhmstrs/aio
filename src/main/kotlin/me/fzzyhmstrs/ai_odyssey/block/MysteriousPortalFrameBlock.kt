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

    private val maxSearchOffset = 10
    
    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        return ActionResult.SUCCESS
    }

    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        TODO("Not yet implemented")
    }

    override fun doorType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.PORTAL
    }
    
    private fun composePortalFrame(world: World, pos: BlockPos): Boolean{
        val layerMap: MutableMap<Int, PortalLayer> = mutableMapOf()
        val axis: Direction.Axis = TODO("need IDE so I can build the Axis finder")
        var bl: Boolean = true
        var offset = 0
        while (bl){
            var bl2 = false
            var offset2 = 1
            val elevation = pos.y + offset
            while (bl2) {
                val newLayer = if (layerMap.containsKey(elevation)){
                    layermap[elevation]?:return false
                } else {
                    PortalLayer(axis)
                }
                val testPos1 = TODO("need IDE")
                val testPos2 = TODO("need IDE")
                if (world.getBlockState(testPos1).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME){
                    newLayer.addFramePos(testPos1)
                    if (!(newLayer.isContinuous || newLayer.size < 2)) {
                        bl2 = false
                        continue
                    }
                }
                if (world.getBlockState(testPos2).isOf(RegisterBlock.MYSTERIOUS_PORTAL_FRAME){
                    newLayer.addFramePos(testPos2)
                    if (!(newLayer.isContinuous || newLayer.size < 2)) {
                        bl2 = false
                        continue
                    }
                }
                offset2++
                if (offset > maxSearchOffset){
                    return false
                }
            }
            offset++
        }
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

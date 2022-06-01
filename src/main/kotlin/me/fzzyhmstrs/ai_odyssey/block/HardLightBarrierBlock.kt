package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.EntityShapeContext
import net.minecraft.block.ShapeContext
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.function.ToIntFunction

class HardLightBarrierBlock(settings: Settings): Block(settings) {

    companion object {
        private val LOCKED = Properties.LOCKED
        val STATE_TO_LUMINANCE: ToIntFunction<BlockState> = ToIntFunction { state:BlockState -> if(state.get(
                LOCKED
            ) != false){15} else {8} }
    }

    init{
        defaultState = stateManager.defaultState.with(LOCKED,true)
    }

    fun unlock(state: BlockState, world: World, pos: BlockPos){
        world.setBlockState(pos,state.cycle(LOCKED))
    }

    override fun isTranslucent(state: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return true
    }

    override fun canMobSpawnInside(): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun canPathfindThrough(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        type: NavigationType
    ): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun getCollisionShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        if (context is EntityShapeContext){
            val prop = state.getOrEmpty(LOCKED)
            if (prop.isPresent){
                if (!prop.get()){
                    return VoxelShapes.empty()
                }
            }
        }
        return if (collidable) state.getOutlineShape(world, pos) else VoxelShapes.empty()
    }

}
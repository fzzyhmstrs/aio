package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.function.ToIntFunction

class CrystallineLanternBlock(settings: Settings): Block(settings) {

    companion object{

        private val LIT = Properties.LIT
        val STATE_TO_LUMINANCE: ToIntFunction<BlockState> = ToIntFunction {state:BlockState -> if(state.get(LIT) != false){15} else {10} }
    }

    init{
        defaultState = stateManager.defaultState.with(LIT,true)
    }

    fun turnOff(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.with(LIT,false))
    }
    fun turnOn(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.with(LIT,true))
    }
    fun flipSwitch(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.cycle(LIT))
    }

}
package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import java.util.function.ToIntFunction

class CrystallineLanternBlock(settings: Settings): Block(settings) {

    companion object{
        private val LIT = EnumProperty.of("lantern_lit", LanternLit::class.java)
        val STATE_TO_LUMINANCE: ToIntFunction<BlockState> = ToIntFunction {state:BlockState -> state.get(LIT).luminance() }

        enum class LanternLit: StringIdentifiable{
            LIT {
                override fun luminance(): Int {
                    return 15
                }
            },
            EMERGENCY {
                override fun luminance(): Int {
                    return 10
                }
            },
            OFF {
                override fun luminance(): Int {
                    return 0
                }
            };

            abstract fun luminance():Int

            override fun asString(): String {
                return this.name
            }
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(LIT,LanternLit.LIT)
    }

    fun turnOff(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.with(LIT,LanternLit.OFF))
    }
    fun turnOn(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.with(LIT,LanternLit.LIT))
    }
    fun turnEmergency(state:BlockState, pos: BlockPos, world: World){
        world.setBlockState(pos, state.with(LIT,LanternLit.EMERGENCY))
    }

    @Deprecated("Deprecated in Java")
    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        val prop = newState.getOrEmpty(LIT)
        if (prop.isPresent){
            if(prop.orElse(LanternLit.LIT) == LanternLit.OFF){
                world.createAndScheduleBlockTick(pos, this, 1200)
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    @Deprecated("Deprecated in Java")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (state.isOf(this)){
            val prop = state.getOrEmpty(LIT)
            if (prop.isPresent){
                if(prop.orElse(LanternLit.LIT) == LanternLit.OFF){
                    world.setBlockState(pos,state.with(LIT,LanternLit.LIT))
                }
            }
        }
    }

}
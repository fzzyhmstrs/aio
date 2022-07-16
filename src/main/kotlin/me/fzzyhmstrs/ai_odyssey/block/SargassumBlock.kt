package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.*
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

class SargassumBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        private val AGE = IntProperty.of("age", 0, 150)
        private const val growthChance = 0.1
    }

    private fun getStreamer(): SargassumStreamerBlock{
        return RegisterBlock.SARGASSUM_STREAMER
    }
    
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(AGE)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(AGE,ctx.world.random.nextInt(150))
    }

    override fun hasRandomTicks(state: BlockState): Boolean {
        return state.get(AGE) < 150
    }
    
    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val age = state.get(AGE)
        if (age < 150 &&
            random.nextDouble() < growthChance){
            val direction = checkForOpenOcean(world,pos)
            if (direction == Direction.DOWN){
                TODO("need a sargassum streamer thingy")
            } else {
                world.setBlockState(pos.offset(direction), this.defaultState.with(AGE,age + 1))
            }
        }
        
    }
    
    private fun checkForOpenOcean(world: ServerWorld, pos: BlockPos): Direction{
        val oceanList: MutableList<Direction> = mutableListOf()
        for (dir in Direction.Type.HORIZONTAL){
            val chkPos = pos.offset(dir)
            val chkState = world.getBlockState(chkPos)
            if (canPlaceAt(chkState, world, chkPos)){
                oceanList.add(dir)
            }
        }
        return if (oceanList.isNotEmpty()){
            oceanList[world.random.nextInt(oceanList.size)]
        } else {
            Direction.DOWN
        }
    }
    
    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return state.isOf(Blocks.WATER) || (state.material.isReplaceable && state.fluidState.isIn(FluidTags.WATER))
    }
    
    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getStill(false)
    }

    override fun canFillWithFluid(world: BlockView?, pos: BlockPos?, state: BlockState?, fluid: Fluid?): Boolean {
        return false
    }

    override fun tryFillWithFluid(
        world: WorldAccess?,
        pos: BlockPos?,
        state: BlockState?,
        fluidState: FluidState?
    ): Boolean {
        return false
    }
}

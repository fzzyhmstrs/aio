package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidFillable
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.state.property.IntProperty

class SargassumBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        private val AGE = IntProperty.of("age", 0, 49)
    }
    
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(AGE)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        TODO("figure out the random aging thing")
        return super.getPlacementState(ctx)?.with(AGE,0)
    }
    
    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val age = state.get(AGE)
        if (age < 49 &&
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
        val oceanList: MutableList<Direciton> = mutableListOf()
        for (dir in Properties.FACING){
            val chkPos = pos.offset(dir)
            val chkState = world.getBlockState(chkPos)
            if (canPlaceAt(chkState, world, chkPos)){
                list.add(dir)
            }
        }
        if (list.isNotEmpty()){
            return list[world.random.nextInt(list.size)]?:Direction.DOWN
        } else {
            return Direction.DOWN
        }
    }
    
    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return state.ifOf(Blocks.WATER)
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

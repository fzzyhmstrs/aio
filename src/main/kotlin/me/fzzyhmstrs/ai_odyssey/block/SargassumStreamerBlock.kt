package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidFillable
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.state.property.*
import net.minecraft.tag.FluidTags
import net.minecraft.world.WorldView

class SargassumStreamerBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        private val AGE = Properties.AGE_7
        private val END = Properties.VINE_END
    }
    
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(AGE, END)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(AGE,ctx.world.random.nextInt(7))?.with(END,true)
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

package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidFillable
import net.minecraft.block.enums.WallShape
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class PrismaticCrystalBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{
        val UP = Properties.UP
        val EAST_SHAPE = Properties.EAST_WALL_SHAPE
        val NORTH_SHAPE = Properties.NORTH_WALL_SHAPE
        val SOUTH_SHAPE = Properties.SOUTH_WALL_SHAPE
        val WEST_SHAPE = Properties.WEST_WALL_SHAPE
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val initialState = super.getPlacementState(ctx)
        val world = ctx.world
        val pos = ctx.blockPos
        var bl = false
        var placementState: BlockState? = initialState
        if (shouldConnectTo(world, pos, Direction.UP)){
            placementState = placementState?.with(UP,true)
            bl = true
        }
        if (shouldConnectTo(world, pos, Direction.EAST)){
            placementState = placementState?.with(EAST_SHAPE,wallShape(bl))
        }
        return placementState
    }

    private fun shouldConnectTo(world: World, pos: BlockPos, direction: Direction): Boolean{
        val face = direction.opposite
        val state2 = world.getBlockState(pos.offset(direction))
        return (state2.block is PrismaticCrystalBlock) || state2.isSideSolidFullSquare(world, pos, face)
    }

    private fun wallShape(bl: Boolean): WallShape{
        return if (bl){
            WallShape.TALL
        } else {
            WallShape.LOW
        }
    }

    override fun canFillWithFluid(world: BlockView?, pos: BlockPos?, state: BlockState?, fluid: Fluid?): Boolean {
        TODO("Not yet implemented")
    }

    override fun tryFillWithFluid(
        world: WorldAccess?,
        pos: BlockPos?,
        state: BlockState?,
        fluidState: FluidState?
    ): Boolean {
        TODO("Not yet implemented")
    }


}
package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

@Suppress("DeprecatedCallableAddReplaceWith")
abstract class AbstractLockBlock(settings: Settings): BlockWithEntity(settings) {

    companion object {
        val FACING: DirectionProperty = HorizontalFacingBlock.FACING
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(FACING,ctx.playerFacing.opposite)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    @Deprecated("Deprecated in Java")
    override fun getCollisionShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return LecternBlock.COLLISION_SHAPE
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape? {
        return when (state.get(LecternBlock.FACING)) {
            Direction.NORTH -> LecternBlock.NORTH_SHAPE
            Direction.SOUTH -> LecternBlock.SOUTH_SHAPE
            Direction.EAST -> LecternBlock.EAST_SHAPE
            Direction.WEST -> LecternBlock.WEST_SHAPE
            else -> LecternBlock.BASE_SHAPE
        }
    }
}
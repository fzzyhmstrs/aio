package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class CrystallineSwitchPedestal(settings: Settings): Block(settings) {

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return VOXEL_SHAPE
    }

    companion object{
        private val VOXEL_SHAPE = createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0)
    }
}

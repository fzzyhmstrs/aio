package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class MysteriousPortalBlock(settings: Settings): Block(settings) {
    val AXIS = Properties.HORIZONTAL_AXIS
    private val X_SHAPE = createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)
    private val Z_SHAPE = createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return when (state.get(AXIS)) {
            Direction.Axis.Z -> {
                Z_SHAPE
            }
            else -> {
                X_SHAPE
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (world is ServerWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals() && VoxelShapes.matchesAnywhere(
                VoxelShapes.cuboid(entity.boundingBox.offset(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())),
                state.getOutlineShape(world, pos),
                BooleanBiFunction.AND
            )
        ) {
            val registryKey = if (world.getRegistryKey() === World.END) World.OVERWORLD else World.END
            val serverWorld = world.server.getWorld(registryKey) ?: return
            entity.moveToWorld(serverWorld)
        }
    }

    override fun getPickStack(world: BlockView, pos: BlockPos, state: BlockState): ItemStack {
        return ItemStack.EMPTY
    }

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun canBucketPlace(state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }
}
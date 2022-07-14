package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.KelpBlock
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.abs

class MidnightKelpBlock(settings: Settings): KelpBlock(settings) {

    override fun getPlant(): Block {
        return RegisterBlock.MIDNIGHT_KELP_PLANT
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return VOXEL_SHAPE
    }
    
    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.85, 0.85, 0.85))
        if (!(world.isClient || entity.lastRenderX == entity.getX() && entity.lastRenderY == entity.getY() && entity.lastRenderZ == entity.getZ())) {
            val d = abs(entity.getX() - entity.lastRenderX)
            val e = abs(entity.getY() - entity.lastRenderY)
            val f = abs(entity.getZ() - entity.lastRenderZ)
            if (d >= 0.003 || e >= 0.003 || f >= 0.003) {
                entity.damage(DamageSource.SWEET_BERRY_BUSH, 0.75f)
                if (entity.isDead){
                    println("I died!: $entity")
                    if (world is ServerWorld) {
                        this.grow(world, world.random, pos, state)
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun canPathfindThrough(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        type: NavigationType
    ): Boolean {
        return true
    }

    companion object{
        private val VOXEL_SHAPE = createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0)
    }

}

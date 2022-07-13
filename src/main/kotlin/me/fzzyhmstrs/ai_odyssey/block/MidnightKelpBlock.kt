package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.KelpBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.abs

class MidnightKelpBlock(settings: Settings): KelpBlock(settings) {

    override fun getPlant(): Block {
        return RegisterBlock.MIDNIGHT_KELP_PLANT
    }
    
    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.8, 0.8, 0.8))
        if (!(world.isClient || entity.lastRenderX == entity.getX() && entity.lastRenderZ == entity.getZ())) {
            val d = abs(entity.getX() - entity.lastRenderX)
            val e = abs(entity.getZ() - entity.lastRenderZ)
            if (d >= 0.003 || e >= 0.003) {
                entity.damage(DamageSource.SWEET_BERRY_BUSH, 1.0f)
            }
        }
    }

}

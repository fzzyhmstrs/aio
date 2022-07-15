package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_core.entity_util.MissileEntity
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.world.World

class VampiricBoltEntity(entityType: EntityType<out VampiricBoltEntity?>, world: World): MissileEntity(entityType, world) {

    constructor(world: World, owner: LivingEntity, speed: Float, divergence: Float, x: Double, y: Double, z: Double) : this(
        RegisterEntity.VAMPIRIC_BOLT_ENTITY,world){
        this.owner = owner
        this.setVelocity(owner,
            owner.pitch,
            owner.yaw,
            0.0f,
            speed,
            divergence)
        this.setPosition(x,y,z)
        this.setRotation(owner.yaw, owner.pitch)
    }

    override var entityEffects: AugmentEffect = AugmentEffect().withDamage(3.0F).withAmplifier(2)

    override fun passEffects(ae: AugmentEffect, level: Int) {
        super.passEffects(ae, level)
        ae.addDuration(ae.amplifier(level))
    }
    
    override fun onEntityHit(entityHitResult: EntityHitResult) {
        if (world.isClient) {
            return
        }
        val entity = owner
        if (entity is LivingEntity) {
            val entity2 = entityHitResult.entity
            val bl = entity2.damage(
                DamageSource.thrownProjectile(this,owner),
                entityEffects.damage(0)
            )
            if (bl) {
                entityEffects.accept(entity, AugmentConsumer.Type.BENEFICIAL)
                entity.heal(entityEffects.amplifier(0).toFloat())
                applyDamageEffects(entity as LivingEntity?, entity2)
                if (entity2 is LivingEntity) {
                    entityEffects.accept(entity2, AugmentConsumer.Type.HARMFUL)
                }
            }
        }
        discard()
    }

}

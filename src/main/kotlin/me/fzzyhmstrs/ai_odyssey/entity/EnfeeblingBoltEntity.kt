package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_core.entity_util.MissileEntity
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.projectile.SmallFireballEntity
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class EnfeeblingBoltEntity(entityType: EntityType<out EnfeeblingBoltEntity?>, world: World): MissileEntity(entityType, world) {

    constructor(world: World, owner: LivingEntity, speed: Float, divergence: Float, x: Double, y: Double, z: Double) : this(
        RegisterEntity.ENFEEBLING_BOLT_ENTITY,world){
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

    override var entityEffects: AugmentEffect = AugmentEffect().withDamage(6.0F).withDuration(15)

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
                applyDamageEffects(entity as LivingEntity?, entity2)
                if (entity2 is LivingEntity) {
                    EffectQueue.addStatusToQueue(entity2,StatusEffects.WEAKNESS,entityEffects.duration(0),entityEffects.amplifier(0))
                    EffectQueue.addStatusToQueue(entity2,StatusEffects.SLOWNESS,entityEffects.duration(0),entityEffects.amplifier(0))
                    entityEffects.accept(entity2, AugmentConsumer.Type.HARMFUL)
                }
            }
        }
        discard()
    }

    companion object{
        fun createEnfeeblingBolt(world: World, user: LivingEntity, speed: Float, div: Float, effects: AugmentEffect, level: Int): EnfeeblingBoltEntity {
            val fbe = EnfeeblingBoltEntity(
                world, user, speed, div,
                user.x - (user.width + 0.5f) * 0.5 * MathHelper.sin(user.bodyYaw * (Math.PI.toFloat() / 180)) * MathHelper.cos(
                    user.pitch * (Math.PI.toFloat() / 180)
                ),
                user.eyeY - 0.6 - 0.8 * MathHelper.sin(user.pitch * (Math.PI.toFloat() / 180)),
                user.z + (user.width + 0.5f) * 0.5 * MathHelper.cos(user.bodyYaw * (Math.PI.toFloat() / 180)) * MathHelper.cos(
                    user.pitch * (Math.PI.toFloat() / 180)
                ),
            )
            fbe.passEffects(effects, level)
            return fbe
        }
    }

}

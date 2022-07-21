package me.fzzyhmstrs.ai_odyssey.entity

import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.world.World

class HarpoonEntity: PersistentProjectileEntity {
    constructor(entityType: EntityType<out HarpoonEntity?>, world: World) : super(entityType, world)
    constructor(world: World, x: Double, y: Double, z: Double): super(RegisterEntity.HAPROON_ENTITY,x, y, z, world)
    constructor(world: World, owner: LivingEntity): super(RegisterEntity.HAPROON_ENTITY,owner, world)

    init {
        damage = 7.5
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(RegisterItem.HARPOON)
    }

    override fun getDragInWater(): Float {
        return 0.99F
    }

    override fun isCritical(): Boolean {
        return false
    }

    override fun isShotFromCrossbow(): Boolean {
        return false
    }

    override fun getPierceLevel(): Byte {
        return 0
    }

    override fun getHitSound(): SoundEvent {
        return SoundEvents.ITEM_TRIDENT_HIT
    }

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        val damageSource: DamageSource
        var entity2: Entity?
        super.onEntityHit(entityHitResult)
        val entity = entityHitResult.entity
        if (owner.also { entity2 = it } == null) {
            damageSource = DamageSource.trident(this, this)
        } else {
            damageSource = DamageSource.trident(this, entity2)
            if (entity2 is LivingEntity) {
                (entity2 as LivingEntity).onAttacking(entity)
            }
        }
        val bl = entity.type === EntityType.ENDERMAN
        val j = entity.fireTicks
        if (this.isOnFire && !bl) {
            entity.setOnFireFor(5)
        }
        if (entity.damage(damageSource, damage.toFloat())) {
            if (bl) {
                return
            }
            if (entity is LivingEntity) {
                val vec3d = velocity.multiply(1.0, 0.0, 1.0).normalize().multiply(punch.toDouble() * 0.6)
                if (punch > 0 && vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.1, vec3d.z)
                }
                if (!world.isClient && entity2 is LivingEntity) {
                    EnchantmentHelper.onUserDamaged(entity, entity2)
                    EnchantmentHelper.onTargetDamaged(entity2 as LivingEntity?, entity)
                }
                onHit(entity)
                if (entity2 != null && entity !== entity2 && entity is PlayerEntity && entity2 is ServerPlayerEntity && !this.isSilent) {
                    (entity2 as ServerPlayerEntity).networkHandler.sendPacket(
                        GameStateChangeS2CPacket(
                            GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER,
                            GameStateChangeS2CPacket.DEMO_OPEN_SCREEN.toFloat()
                        )
                    )
                }
            }
            playSound(sound, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.9f))
        } else {
            entity.fireTicks = j
            velocity = velocity.multiply(-0.1)
            yaw += 180.0f
            prevYaw += 180.0f
            if (!world.isClient && velocity.lengthSquared() < 1.0E-7) {
                if (pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(asItemStack(), 0.1f)
                }
                discard()
            }
        }
    }
}


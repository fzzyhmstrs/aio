package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.AIO_Client
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import me.fzzyhmstrs.ai_odyssey.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_core.ACC
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.registry.SyncedConfigRegistry
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.SlashAugment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.MobEntity
import net.minecraft.item.Items
import net.minecraft.network.PacketByteBuf
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*
import kotlin.random.asJavaRandom

class ResonateAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SlashAugment(tier, maxLvl, *slot) {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDamage(4.0F,1.0F,0.0F)
            .withRange(3.5,0.25,0.0)
            .withDuration(36,4)


    override fun effect(world: World, user: LivingEntity, entityList: MutableList<Entity>, level: Int, effect: AugmentEffect): Boolean {
        val entityDistance: SortedMap<Double, Entity> = mutableMapOf<Double, Entity>().toSortedMap()
        for (entity in entityList){
            if (entity is MobEntity){
                val dist = entity.squaredDistanceTo(user)
                entityDistance[dist] = entity
            }
        }
        var bl = false
        if (entityDistance.isNotEmpty()) {
            val entityDistance2 = entityDistance.toList()
            val entity1 = entityDistance2[0].second
            bl = resonateTarget(world,user,entity1,level, effect)
            if (entityDistance2.size > 1 && level > 1){
                val entity2 = entityDistance2[1].second
                bl = bl || resonateTarget(world, user, entity2, level, effect, true)
                if (entityDistance2.size > 2 && level > 2){
                    val entity3 = entityDistance2[2].second
                    bl = bl || resonateTarget(world, user, entity3, level, effect, true)
                }
            }
            if (bl){
                effect.accept(user, AugmentConsumer.Type.BENEFICIAL)
            }
            effect.accept(toLivingEntityList(entityList), AugmentConsumer.Type.HARMFUL)
        }
        return bl
    }

    private fun resonateTarget(world: World,user: LivingEntity,target: Entity,level: Int,effect: AugmentEffect, splash: Boolean = false): Boolean{
        val amp = if (target is LivingEntity){
            val status = target.getStatusEffect(RegisterStatus.RESONATING)
            status?.amplifier?:-1
        } else {
            -1
        }
        println(amp)
        val damage = if(!splash) {
            effect.damage(level + amp + 1)
        } else {
            effect.damage(level + amp - 1)
        }
        val bl = target.damage(DamageSource.mob(user),damage)
        println(damage)
        if (bl) {
            if (user is ServerPlayerEntity) {
                ServerPlayNetworking.send(user, NOTE_BLAST, writeBuf(user, target))
            }
            secondaryEffect(world, user, target, level, effect)
        }
        return bl
    }

    /*private fun noteBlast(user: LivingEntity, target: Entity){
        val userPos = user.eyePos.add(0.0,-0.3,0.0)
        val direction = userPos.subtract(target.pos).normalize()
        println(direction)
        val perpendicularToPosX = 1.0
        val perpendicularToPosZ = (direction.x/direction.z) * -1
        val perpendicularVector = Vec3d(perpendicularToPosX,0.0,perpendicularToPosZ).normalize()
        for (i in 1..10){
            val rnd1 = user.world.random.nextDouble() - 0.5
            val rnd2 = user.world.random.nextDouble() - 0.5
            val particlePos = userPos.add(perpendicularVector.multiply(rnd1)).add(0.0, rnd2,0.0)
            val particleVelocity = direction.multiply(particleSpeed).add(user.velocity)
            addParticles(user.world,particleType(),particlePos,particleVelocity)
        }
    }*/

    override fun clientTask(world: World, user: LivingEntity, hand: Hand, level: Int) {
    }

    override fun secondaryEffect(world: World, user: LivingEntity, target: Entity, level: Int, effect: AugmentEffect) {
        if (target is LivingEntity){
            val status = target.getStatusEffect(RegisterStatus.RESONATING)
            val amp = status?.amplifier?:-1
            target.addStatusEffect(addStatusInstance(effect,amp + 1))
        }
    }


    override fun addStatusInstance(effect: AugmentEffect, level: Int): StatusEffectInstance {
        return StatusEffectInstance(RegisterStatus.RESONATING,effect.duration(level), level)
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_BELL_RESONATE
    }

    override fun particleType(): DefaultParticleType {
        return ParticleTypes.ELECTRIC_SPARK
    }

    override fun particleSpeed(): Double {
        return 3.0
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.FURY,18,15,15,imbueLevel, LoreTier.NO_TIER, Items.NOTE_BLOCK)
    }

    companion object {

        private val NOTE_BLAST = Identifier(AIO.MOD_ID, "note_blast")

        internal fun registerClient() {
            ClientPlayNetworking.registerGlobalReceiver(NOTE_BLAST) { client, _, buf, _ ->
                val userX = buf.readDouble()
                val userY = buf.readDouble()
                val userZ = buf.readDouble()
                val userPos = Vec3d(userX,userY,userZ)
                val userVelX = buf.readDouble()
                val userVelY = buf.readDouble()
                val userVelZ = buf.readDouble()
                val userVel = Vec3d(userVelX,userVelY,userVelZ)
                val targetX = buf.readDouble()
                val targetY = buf.readDouble()
                val targetZ = buf.readDouble()
                val targetPos = Vec3d(targetX,targetY,targetZ)
                noteBlast(userPos,userVel, targetPos, client.world)
            }
        }

        private fun noteBlast(userPos: Vec3d,userVel: Vec3d, targetPos:Vec3d, world: World?){
            if (world == null) return
            val random = world.random
            val direction = targetPos.subtract(userPos).normalize()
            val perpendicularToPosX = 1.0
            val perpendicularToPosZ = (direction.x/direction.z) * -1
            val perpendicularVector = Vec3d(perpendicularToPosX,0.0,perpendicularToPosZ).normalize()
            for (i in 1..10){
                val rnd1 = (random.nextDouble() - 0.5)/2.0
                val rnd2 = (random.nextDouble() - 0.5)/2.0
                val particlePos = userPos.add(perpendicularVector.multiply(rnd1)).add(0.0, rnd2,0.0)
                val particleVelocity = direction.multiply(RegisterEnchantment.RESONATE.particleSpeed()).add(userVel)
                addParticles(world,RegisterEnchantment.RESONATE.particleType(),particlePos,particleVelocity)
            }
        }

        private fun addParticles(world: World, particleEffect: ParticleEffect, pos: Vec3d, velocity: Vec3d){
            world.addParticle(particleEffect,true,pos.x,pos.y,pos.z,velocity.x,velocity.y,velocity.z)
        }

        internal fun writeBuf(user: LivingEntity, target: Entity): PacketByteBuf{
            val buf = PacketByteBufs.create()
            val userPos = user.eyePos.add(0.0,-0.4,0.0)
            val userVel = user.velocity
            val targetPos = target.pos .add(0.0,target.height / 2.0,0.0)
            buf.writeDouble(userPos.x)
            buf.writeDouble(userPos.y)
            buf.writeDouble(userPos.z)
            buf.writeDouble(userVel.x)
            buf.writeDouble(userVel.y)
            buf.writeDouble(userVel.z)
            buf.writeDouble(targetPos.x)
            buf.writeDouble(targetPos.y)
            buf.writeDouble(targetPos.z)
            return buf
        }

    }
}
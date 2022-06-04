package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_imbuement.scepter.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentConsumer
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentEffect
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.MinorSupportAugment
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class SmitingBlowAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier, maxLvl, *slot) {

    override val baseEffect: AugmentEffect
        get() = super.baseEffect.withDamage(4.0F,1.0F).withRange(4.0,1.0).withAmplifier(2)

    override fun supportEffect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        effects: AugmentEffect
    ): Boolean {
        return if(target != null) {
            if (target is LivingEntity) {
                val bl = if(target.isUndead) {
                    target.damage(DamageSource.MAGIC,effects.damage(level) * effects.amplifier(level))
                } else {
                    target.damage(DamageSource.MAGIC,effects.damage(level))
                }
                if (bl) {
                    effects.accept(target,AugmentConsumer.Type.HARMFUL)
                    effects.accept(user,AugmentConsumer.Type.BENEFICIAL)
                    generateParticles(world,target.pos)
                    world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F)
                }
                bl
            } else {
                false
            }
        } else {
            false
        }
    }

    private fun generateParticles(world: World, pos: Vec3d){
        for (i in 1..8){
            val rnd1 = world.random.nextDouble() - 0.5
            val rnd2 = world.random.nextDouble() - 0.5
            val rnd3 = -3.0 + (world.random.nextDouble() - 0.5) * 0.1
            world.addParticle(ParticleTypes.ENCHANTED_HIT,true,pos.x,pos.add(rnd1,2.3,rnd2).y,pos.z,0.0,rnd3,0.0)
        }
        for (i in 1..8){
            val rnd1 = world.random.nextDouble() - 0.5
            val rnd2 = world.random.nextDouble() - 0.5
            val rnd3 = -3.0 + (world.random.nextDouble() - 0.5) * 0.1
            world.addParticle(ParticleTypes.ENCHANTED_HIT,true,pos.x,pos.add(rnd1,2.0,rnd2).y,pos.z,0.0,rnd3,0.0)
        }
        for (i in 1..8){
            val rnd1 = world.random.nextDouble() - 0.5
            val rnd2 = world.random.nextDouble() - 0.5
            val rnd3 = -3.0 + (world.random.nextDouble() - 0.5) * 0.1
            world.addParticle(ParticleTypes.ENCHANTED_HIT,true,pos.x,pos.add(rnd1,2.6,rnd2).y,pos.z,0.0,rnd3,0.0)
        }
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_BELL_USE
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.FURY,20,8,10,imbueLevel,LoreTier.LOW_TIER, Items.ROTTEN_FLESH)
    }
}
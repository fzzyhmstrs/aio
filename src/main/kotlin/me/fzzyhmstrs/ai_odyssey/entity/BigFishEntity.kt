package me.fzzyhmstrs.ai_odyssey.entity

import net.minecraft.block.BlockState
import net.minecraft.entity.*
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.ai.goal.EscapeDangerGoal
import net.minecraft.entity.ai.goal.FleeEntityGoal
import net.minecraft.entity.ai.goal.SwimAroundGoal
import net.minecraft.entity.ai.pathing.EntityNavigation
import net.minecraft.entity.ai.pathing.SwimNavigation
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.WaterCreatureEntity
import net.minecraft.entity.passive.FishEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

abstract class BigFishEntity(bigFishEntity: EntityType<out BigFishEntity?>, world: World): WaterCreatureEntity(bigFishEntity, world) {

    init{
        this.moveControl = BigFishMoveControl(this)
    }

    override fun getActiveEyeHeight(pose: EntityPose?, dimensions: EntityDimensions): Float {
        return dimensions.height * 0.55f
    }

    override fun getLimitPerChunk(): Int {
        return 2
    }

    protected open fun hasSelfControl(): Boolean {
        return true
    }

    protected abstract fun getFlopSound(): SoundEvent

    override fun getSwimSound(): SoundEvent {
        return SoundEvents.ENTITY_FISH_SWIM
    }

    override fun playStepSound(pos: BlockPos?, state: BlockState?) {}

    override fun tickMovement() {
        if (!this.isTouchingWater && onGround && verticalCollision) {
            velocity = velocity.add(
                ((random.nextFloat() * 2.0f - 1.0f) * 0.03f).toDouble(),
                0.4,
                ((random.nextFloat() * 2.0f - 1.0f) * 0.03f).toDouble()
            )
            onGround = false
            velocityDirty = true
            playSound(getFlopSound(), this.soundVolume, this.soundPitch)
        }
        super.tickMovement()
    }

    override fun initGoals() {
        super.initGoals()
        goalSelector.add(0, EscapeDangerGoal(this, 1.15))
        goalSelector.add(4, SwimToRandomPlaceGoal(this))
    }

    override fun createNavigation(world: World): EntityNavigation {
        return SwimNavigation(this, world)
    }

    override fun travel(movementInput: Vec3d?) {
        if (canMoveVoluntarily() && this.isTouchingWater) {
            updateVelocity(0.01f, movementInput)
            move(MovementType.SELF, velocity)
            velocity = velocity.multiply(0.9)
            if (target == null) {
                velocity = velocity.add(0.0, -0.008, 0.0)
            }
        } else {
            super.travel(movementInput)
        }
    }

    internal class BigFishMoveControl(private val fish: BigFishEntity) : MoveControl(fish) {
        override fun tick() {
            if (fish.isSubmergedIn(FluidTags.WATER)) {
                fish.velocity = fish.velocity.add(0.0, 0.003, 0.0)
            }
            if (state != State.MOVE_TO || fish.navigation.isIdle) {
                fish.movementSpeed = 0.0f
                return
            }
            val f = (speed * fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)).toFloat()
            fish.movementSpeed = MathHelper.lerp(0.125f, fish.movementSpeed, f)
            val d = targetX - fish.x
            val e = targetY - fish.y
            val g = targetZ - fish.z
            if (e != 0.0) {
                val h = Math.sqrt(d * d + e * e + g * g)
                fish.velocity = fish.velocity.add(0.0, fish.movementSpeed.toDouble() * (e / h) * 0.1, 0.0)
            }
            //add a slow turn effect here?
            if (d != 0.0 || g != 0.0) {
                val i = (MathHelper.atan2(g, d) * 57.2957763671875).toFloat() - 90.0f
                fish.yaw = wrapDegrees(fish.yaw, i, 90.0f)
                fish.bodyYaw = fish.yaw
            }
        }
    }

    internal class SwimToRandomPlaceGoal(private val fish: BigFishEntity) : SwimAroundGoal(fish, 0.8, 200) {
        override fun canStart(): Boolean {
            return fish.hasSelfControl() && super.canStart()
        }
    }

    companion object{
        fun createBigFishAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
        }

    }

}
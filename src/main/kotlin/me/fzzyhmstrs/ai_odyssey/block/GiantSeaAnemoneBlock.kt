package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidFillable
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.TropicalFishEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.tag.FluidTags
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import kotlin.math.abs

class GiantSeaAnemoneBlock(settings: Settings): Block(settings), FluidFillable {

    companion object{

        private val FACING = EnumProperty.of("facing",CompassDirection::class.java)

        enum class CompassDirection(private val str: String): StringIdentifiable{
            NORTH("north"),
            NORTHEAST("northeast"),
            EAST("east"),
            SOUTHEAST("southeast"),
            SOUTH("south"),
            SOUTHWEST("southwest"),
            WEST("west"),
            NORTHWEST("northwest"),
            CENTER("center");

            override fun asString(): String {
                return this.str
            }
        }


    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        return if (fluidState.isIn(FluidTags.WATER) &&
            fluidState.level == 8)
        {
            super.getPlacementState(ctx)?.with(FACING,composeShape(ctx.world,ctx.blockPos, ctx.playerFacing))
        } else null
    }

    private fun composeShape(world: World,pos: BlockPos, facing: Direction): CompassDirection{
        val chkNorth = world.getBlockState(pos.offset(Direction.NORTH)).isOf(this)
        val chkEast = world.getBlockState(pos.offset(Direction.EAST)).isOf(this)
        val chkSouth = world.getBlockState(pos.offset(Direction.SOUTH)).isOf(this)
        val chkWest = world.getBlockState(pos.offset(Direction.WEST)).isOf(this)

        if (!(chkNorth && chkEast && chkSouth && chkWest)){
            return when(facing){
                Direction.NORTH->CompassDirection.NORTH
                Direction.EAST->CompassDirection.EAST
                Direction.SOUTH->CompassDirection.SOUTH
                Direction.WEST->CompassDirection.WEST
                else -> CompassDirection.CENTER
            }
        } else if(chkNorth && !chkEast && !chkSouth && !chkWest){
            return CompassDirection.NORTH
        } else if(!chkNorth && chkEast && !chkSouth && !chkWest){
            return CompassDirection.EAST
        } else if(!chkNorth && !chkEast && chkSouth && !chkWest){
            return CompassDirection.SOUTH
        } else if(!chkNorth && !chkEast && !chkSouth && chkWest){
            return CompassDirection.WEST
        } else if(chkNorth && chkEast && !chkSouth && !chkWest){
            return CompassDirection.NORTHEAST
        } else if(chkNorth && !chkEast && !chkSouth && chkWest){
            return CompassDirection.NORTHWEST
        } else if(!chkNorth && chkEast && chkSouth && !chkWest){
            return CompassDirection.SOUTHEAST
        } else if(!chkNorth && !chkEast && chkSouth && chkWest){
            return CompassDirection.SOUTHWEST
        } else if(chkNorth && chkEast && !chkSouth && chkWest){
            return CompassDirection.NORTH
        } else if(chkNorth && chkEast && chkSouth && !chkWest){
            return CompassDirection.EAST
        } else if(!chkNorth && chkEast && chkSouth && chkWest){
            return CompassDirection.SOUTH
        } else if(chkNorth && !chkEast && chkSouth && chkWest){
            return CompassDirection.WEST
        } else {
            return CompassDirection.CENTER
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity !is LivingEntity) {
            return
        }
        entity.slowMovement(state, Vec3d(0.8, 0.8, 0.8))
        if (!(world.isClient || entity.lastRenderX == entity.getX() && entity.lastRenderY == entity.getY() && entity.lastRenderZ == entity.getZ())) {
            val d = abs(entity.getX() - entity.lastRenderX)
            val e = abs(entity.getY() - entity.lastRenderY)
            val f = abs(entity.getZ() - entity.lastRenderZ)
            if (d >= 0.003 || e >= 0.003 || f >= 0.003) {
                if (!entity.isDead) {
                    if (!(entity is TropicalFishEntity && (entity.variant == 65536 || entity.variant == 917504))) {
                        entity.damage(DamageSource.GENERIC, 1.0f)
                        val rnd = world.random.nextFloat()
                        if (rnd < 0.1F){
                            EffectQueue.addStatusToQueue(entity,StatusEffects.POISON,100,0)
                        }
                        if (rnd >0.9F){
                            EffectQueue.addStatusToQueue(entity,StatusEffects.SLOWNESS,100,1)
                        }
                    }
                }
            }
        }
    }

    override fun canFillWithFluid(world: BlockView, pos: BlockPos, state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun tryFillWithFluid(
        world: WorldAccess,
        pos: BlockPos,
        state: BlockState,
        fluidState: FluidState
    ): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Fluids.WATER.getStill(false)", "net.minecraft.fluid.Fluids"))
    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getStill(false)
    }

    @Deprecated("Deprecated in Java")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return !state.isOf(Blocks.MAGMA_BLOCK)
    }
}
package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.ConfiguratorPaired
import me.fzzyhmstrs.ai_odyssey.entity.FacilityTeleporterBlockEntity
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FacilityTeleporterBlock(settings: Settings): BlockWithEntity(settings), SwitchDoor {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return FacilityTeleporterBlockEntity(pos, state)
    }
    
    @Deprecated("Deprecated in Java")
    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.FACILITY_TELEPORTER_BLOCK_ENTITY)
        return if (entity != null) {
            val nbt = stack.orCreateNbt
            val id = ConfiguratorPaired.getParentId(nbt, parentKey)
            val parentTeleporter = ConfiguratorPaired.getParentFromId(id)
            if (ConfiguratorPaired.hasParent(nbt, parentKey) && parentTeleporter?.pos != pos){
                if (parentTeleporter is FacilityTeleporterBlockEntity) {
                    parentTeleporter.setDestination(pos)
                    entity.setDestination(parentTeleporter.pos)
                    user?.sendMessage(TranslatableText("message.teleporter.linked", pos.toShortString(), parentTeleporter.pos.toShortString()), false)
                } else {
                    user?.sendMessage(TranslatableText("message.configurator.parent_fail", id.toString()), false)
                }
            }
            setParent(entity, user, stack)
            ActionResult.SUCCESS
        } else {
            user?.sendMessage(TranslatableText("message.configurator.teleporter_fail", pos.toShortString()), false)
            ActionResult.FAIL
        }

    }

    override fun openDoor(world: World, user: LivingEntity, pos: BlockPos, state: BlockState) {
        val entity = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.FACILITY_TELEPORTER_BLOCK_ENTITY)
        if (entity == null) {
            if (user is PlayerEntity) {
                user.sendMessage(TranslatableText("message.teleporter.fail_entity"), false)
            }
            FacilityChimes.FAILURE.playSound(world, pos)
            return
        } else {
            val destination = entity.getDestination()
            if (destination == null){
                if (user is PlayerEntity) {
                    user.sendMessage(TranslatableText("message.teleporter.fail_destination"), false)
                }
                FacilityChimes.FAILURE.playSound(world, pos)
                return
            } else {
                val tpDest = destination.up()
                user.teleport(tpDest.x + 0.5,tpDest.y.toDouble(), tpDest.z + 0.5)
            }
        }
    }

    override fun doorType(): SwitchDoor.DoorType {
        return SwitchDoor.DoorType.TELEPORTER
    }

    companion object: ConfiguratorPaired {

        override val primedKey: String = "teleporter_primed"
        override val primedPosKey: String = "teleporter_primed_pos"
        override val parentKey: String = "teleporter_parent"

    }
}

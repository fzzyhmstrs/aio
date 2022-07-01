package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.block.CrystallineSwitchBlock
import me.fzzyhmstrs.ai_odyssey.configurator.ConfiguratorPaired
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.configurator.SwitchLock
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult

class FacilityConfigurationStick(settings: Settings): Item(settings), ConfiguratorPaired {

    override val primedKey: String = "switch_primed"
    override val primedPosKey: String = "switch_primed_pos"
    override val parentKey: String = "switch_parent"

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        if (world.isClient) return ActionResult.PASS
        val pos = context.blockPos
        val state = world.getBlockState(pos)
        val player = context.player
        val stack = context.stack
        val block = state.block
        val nbt = stack.orCreateNbt
        val id = ConfiguratorPaired.getParentId(nbt, parentKey)
        val parentSwitch = ConfiguratorPaired.getParentFromId(id)
        if (state.isOf(RegisterBlock.CRYSTALLINE_SWITCH)){
            return if (parentSwitch?.pos == pos){
                context.world.setBlockState(pos,state.cycle(CrystallineSwitchBlock.SWITCH_COLOR))
                ActionResult.SUCCESS
            } else {
                val switch = RegisterEntity.getBlockEntity(world, pos, RegisterEntity.CRYSTALLINE_SWITCH_BLOCK_ENTITY)
                if (switch != null) {
                    setParent(switch, player, stack)
                    ActionResult.SUCCESS
                } else {
                    player?.sendMessage(TranslatableText("message.configurator.entity_fail", pos.toShortString()), false)
                    ActionResult.FAIL
                }
            }
        } else if (block is SwitchDoor){
            return if (parentSwitch is CrystallineSwitchBlockEntity) {
                val bl = parentSwitch.addDoor(pos)
                if (bl){
                    player?.sendMessage(TranslatableText("message.configurator.door_fail", parentSwitch.pos.toShortString() ,pos.toShortString()), false)
                }
                block.interactWithConfigurator(world, player, stack, pos, state)
            } else {
                player?.sendMessage(TranslatableText("message.configurator.parent_fail", id.toString()), false)
                ActionResult.FAIL
            }
        } else if (block is SwitchLock){
            return if (parentSwitch is CrystallineSwitchBlockEntity) {
                val bl = parentSwitch.addLock(pos)
                if (bl){
                    player?.sendMessage(TranslatableText("message.configurator.lock_fail", parentSwitch.pos.toShortString(), pos.toShortString()), false)
                }
                block.interactWithConfigurator(world, player,stack, pos, state)
            } else {
                player?.sendMessage(TranslatableText("message.configurator.parent_fail", id.toString()), false)
                ActionResult.FAIL
            }
        }
        return super.useOnBlock(context)
    }

}
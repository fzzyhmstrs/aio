package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.block.CrystallineSwitchBlock
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.SwitchDoor
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.text.*
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting

class FacilityConfigurationStick(settings: Settings): Item(settings) {

    private var parentSwitch: CrystallineSwitchBlockEntity? = null
    private var primed = false

    fun hasParentSwitch(): Boolean{
        return parentSwitch != null
    }

    private fun setParentSwitch(switch: CrystallineSwitchBlockEntity, playerEntity: PlayerEntity){
        if (primed) {
            parentSwitch = switch
            playerEntity.sendMessage(TranslatableText("message.configurator.configurator_pairing").append(switch.pos.toShortString()), false)
            primed = false
        } else {
            primed = true
            playerEntity.sendMessage(TranslatableText("message.configurator.configurator_pairing_primed"), false)
            if (hasParentSwitch()){
                playerEntity.sendMessage(TranslatableText("message.configurator.configurator_pairing_primed_2").formatted(Formatting.RED), false)
            }
        }
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val pos = context.blockPos
        val state = context.world.getBlockState(pos)
        if (state.isOf(RegisterBlock.CRYSTALLINE_SWITCH)){
            if (parentSwitch?.pos == pos){
                context.world.setBlockState(pos,state.cycle(CrystallineSwitchBlock.SWITCH_COLOR))
                return ActionResult.SUCCESS
            }
        } else if (state.block is SwitchDoor){
            if (parentSwitch?.addDoor(pos) == true){
                return ActionResult.SUCCESS
            }
        }
        return super.useOnBlock(context)
    }
}
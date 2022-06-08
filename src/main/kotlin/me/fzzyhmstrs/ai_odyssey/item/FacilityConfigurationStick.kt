package me.fzzyhmstrs.ai_odyssey.item

import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult

class FacilityConfigurationStick(settings: Settings): Item(settings) {

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val state = context.world.getBlockState(context.blockPos)
        return super.useOnBlock(context)
    }
}
package me.fzzyhmstrs.ai_odyssey.item

import dev.emi.trinkets.api.SlotReference
import me.fzzyhmstrs.ai_odyssey.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_core.item_util.AbstractAugmentJewelryItem
import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

class AmuletOfDepthsItem(settings: Settings): AbstractAugmentJewelryItem(settings) {

    override fun onEquip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        super.onEquip(stack, slot, entity)
        EffectQueue.addStatusToQueue(entity,RegisterStatus.PRESSURE_RESISTANCE,260,1)
    }

    override fun onUnequip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        super.onUnequip(stack, slot, entity)
        entity.removeStatusEffect(RegisterStatus.PRESSURE_RESISTANCE)
    }

    override fun intermittentTick(stack: ItemStack, entity: LivingEntity) {
        super.intermittentTick(stack, entity)
        if(entity.world.isClient()) return
        EffectQueue.addStatusToQueue(entity,RegisterStatus.PRESSURE_RESISTANCE,260,1)
    }

}
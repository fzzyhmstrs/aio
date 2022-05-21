package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_imbuement.scepter.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.PlaceItemAugment
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

class CreateTNTAugment(tier: Int, maxLvl: Int, item: Item, vararg slot: EquipmentSlot): PlaceItemAugment(tier, maxLvl, item, *slot) {
    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.WIT,50,15,5,imbueLevel,LoreTier.LOW_TIER, Items.TNT)
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_GRASS_PLACE
    }

}
package me.fzzyhmstrs.ai_odyssey.augment

import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.ActiveAugment
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterItem
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText

class FuryOfTheConduitAugment(weight: Rarity, mxLvl: Int = 1, vararg slot: EquipmentSlot): ActiveAugment(weight,mxLvl,*slot) {

    override fun activateEffect(user: LivingEntity, level: Int, stack: ItemStack) {
        val lvl = EnchantmentHelper.getLevel(RegisterEnchantment.FURY_OF_THE_CONDUIT,stack)
        val todo: String = "carry the code from the conduit damage thingy over"
        val rnd = user.world.random.nextFloat()
        if (rnd <= 0.40) {
            if (RegisterItem.TOTEM_OF_AMETHYST.manaDamage(stack, user.world, user as PlayerEntity, 1)) {
                RegisterItem.TOTEM_OF_AMETHYST.burnOutHandler(stack, RegisterEnchantment.FURY_OF_THE_CONDUIT, user, TranslatableText("augment_damage.fury_of_the_conduit.burnout"))
            }
        }
    }

}

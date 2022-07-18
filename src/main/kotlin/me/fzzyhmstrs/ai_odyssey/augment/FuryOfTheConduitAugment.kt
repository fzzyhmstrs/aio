package me.fzzyhmstrs.ai_odyssey.augment

import me.fzzyhmstrs.amethyst_core.trinket_util.EffectQueue
import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.ActiveAugment
import me.fzzyhmstrs.amethyst_imbuement.item.TotemItem
import me.fzzyhmstrs.ai_odysseyregistry.RegisterEnchantment
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterItem
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HastingAugment(weight: Rarity, mxLvl: Int = 1, vararg slot: EquipmentSlot): ActiveAugment(weight,mxLvl,*slot) {

    override fun activateEffect(user: LivingEntity, level: Int, stack: ItemStack) {
        val lvl = EnchantmentHelper.getLevel(RegisterEnchantment.HASTING,stack)
        val todo: String = "carry the code from the conduit damage thingy over"
        val rnd = user.world.random.nextFloat()
        if (rnd <= 0.40) {
            if (RegisterItem.TOTEM_OF_AMETHYST.manaDamage(stack, user.world, user as PlayerEntity, 1)) {
                RegisterItem.TOTEM_OF_AMETHYST.burnOutHandler(stack, RegisterEnchantment.HASTING,user, Text.translatable("augment_damage.fury_of_the_conduit.burnout"))
            }
        }
    }

}

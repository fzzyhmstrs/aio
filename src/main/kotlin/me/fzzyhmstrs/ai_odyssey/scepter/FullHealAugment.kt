package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.HealerAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.MinorSupportAugment
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.world.World

class FullHealAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier, maxLvl, *slot),
    HealerAugment {

    override fun supportEffect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        effects: AugmentEffect
    ): Boolean {
        if(target != null) {
            if (target is PassiveEntity || target is GolemEntity || target is PlayerEntity) {
                val currentHealth = (target as LivingEntity).health
                val maxHealth = target.maxHealth
                val healAmount = maxHealth - currentHealth
                if (healAmount == 0.0F) return false
                target.heal(healAmount)
                effects.accept(target, AugmentConsumer.Type.BENEFICIAL)
                world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F)
                return true
            }
        }
        return if (user is PlayerEntity) {
            val currentHealth = user.health
            val maxHealth = user.maxHealth
            val healAmount = maxHealth - currentHealth
            if (healAmount == 0.0F) return false
            user.heal(healAmount)
            effects.accept(user,AugmentConsumer.Type.BENEFICIAL)
            world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F)
            true
        } else {
            false
        }
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.GRACE,200,50,25,imbueLevel, LoreTier.EXTREME_TIER, Items.ENCHANTED_GOLDEN_APPLE)
    }
}
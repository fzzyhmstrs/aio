package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.ai_odyssey.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.BaseAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.*
import me.fzzyhmstrs.amethyst_imbuement.util.LoreTier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class UndeathAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier, maxLvl, *slot), HealerAugment {

    override fun supportEffect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        effects: AugmentEffect
    ): Boolean {
        return if (user is PlayerEntity) {
            BaseAugment.addStatusToQueue(user,RegisterStatus.UNDEATH,1200,0)
            effects.accept(user,AugmentConsumer.Type.BENEFICIAL)
            world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F)
            true
        } else {
            false
        }
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.GRACE,6000,250,25,imbueLevel,LoreTier.NO_TIER, Items.TOTEM_OF_UNDYING)
    }
}
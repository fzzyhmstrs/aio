package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentConsumer
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.raycaster_util.RaycasterUtil
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.FireAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.PlaceItemAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.TravelerAugment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World

class PlaceBedAugment(tier: Int, maxLvl: Int, item: Item, vararg slot: EquipmentSlot): PlaceItemAugment(tier, maxLvl, item, *slot),
    TravelerAugment {

    override fun applyTasks(world: World, user: LivingEntity, hand: Hand, level: Int, effects: AugmentEffect): Boolean {
        val hit = RaycasterUtil.raycastHit(effects.range(level),entity = user)
        var bl = hit != null && hit.type == HitResult.Type.BLOCK
        if (bl){
            val direction = user.horizontalFacing
            val pos1 = (hit as BlockHitResult).blockPos.offset(hit.side)
            val pos2 = pos1.offset(direction)
            if (world.getBlockState(pos2).material.isReplaceable) {
                effects.accept(user, AugmentConsumer.Type.BENEFICIAL)
            } else {
                bl = false
            }
        }
        return bl
    }

    override fun clientTask(world: World, user: LivingEntity, hand: Hand, level: Int) {
        if (user !is PlayerEntity) return
        val hit = MinecraftClient.getInstance().crosshairTarget ?: return
        if (hit.type != HitResult.Type.BLOCK) return
        val direction = user.horizontalFacing
        val pos1 = (hit as BlockHitResult).blockPos.offset(hit.side)
        val pos2 = pos1.offset(direction)
        if (world.getBlockState(pos2).material.isReplaceable) {
            when (val testItem = itemToPlace(world, user).item) {
                is BlockItem -> {
                    testItem.place(ItemPlacementContext(user, hand, ItemStack(testItem), hit))
                    ClientPlayNetworking.send(PLACE_ITEM_PACKET, placeItemPacket(ItemStack(testItem), hand, hit))
                }
                is BucketItem -> {
                    testItem.placeFluid(user, world, hit.blockPos, hit)
                    ClientPlayNetworking.send(PLACE_ITEM_PACKET, placeItemPacket(ItemStack(testItem), hand, hit))
                }
                else -> {
                    return
                }
            }
        } else {
            return
        }
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.WIT,1200,10,1,imbueLevel, LoreTier.LOW_TIER, Items.WHITE_BED)
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.BLOCK_WOOD_PLACE
    }

}
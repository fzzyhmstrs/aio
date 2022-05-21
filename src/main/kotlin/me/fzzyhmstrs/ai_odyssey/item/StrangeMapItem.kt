package me.fzzyhmstrs.ai_odyssey.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.EmptyMapItem
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.tag.TagKey
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import net.minecraft.world.gen.feature.ConfiguredStructureFeature

class StrangeMapItem(settings: Settings): EmptyMapItem(settings) {

    val destination: TagKey<ConfiguredStructureFeature<*, *>>? = null

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        if (world.isClient) {
            return TypedActionResult.success(itemStack)
        }
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1)
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
        user.world.playSoundFromEntity(
            null,
            user,
            SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT,
            user.soundCategory,
            1.0f,
            1.0f
        )
        return TypedActionResult.success(itemStack)
    }

}
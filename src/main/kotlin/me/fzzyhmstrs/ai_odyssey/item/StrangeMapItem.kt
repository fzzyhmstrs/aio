package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.registry.RegisterTag
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.EmptyMapItem
import net.minecraft.item.FilledMapItem
import net.minecraft.item.ItemStack
import net.minecraft.item.map.MapIcon
import net.minecraft.item.map.MapState
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class StrangeMapItem(settings: Settings): EmptyMapItem(settings), Flavorful<StrangeMapItem> {
    
    override var flavor: String = ""
    override var glint: Boolean = false
    override var flavorDesc: String = ""
    
    override fun getFlavorItem(): StrangeMapItem {
        return this
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)
        addFlavorText(tooltip, context)
    }

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
        if (world is ServerWorld) {
            val itemStack2 = createPortalFacilityMap(world, user)
            if (itemStack2 != null){
                if (itemStack.isEmpty) {
                    return TypedActionResult.consume(itemStack2)
                }
                if (!user.inventory.insertStack(itemStack2.copy())) {
                    user.dropItem(itemStack2, false)
                }
                return TypedActionResult.consume(itemStack)
            }

        }
        return TypedActionResult.success(itemStack)
    }

    private fun createPortalFacilityMap(world: ServerWorld, user: PlayerEntity): ItemStack?{
        val facilityPos = world.locateStructure(RegisterTag.PORTAL_FACILITY_TAG,user.blockPos,75,false)
        if (facilityPos != null){
            val stack = FilledMapItem.createMap(world,facilityPos.x,facilityPos.z,2,true,true)
            FilledMapItem.fillExplorationMap(world, stack)
            MapState.addDecorationsNbt(stack, facilityPos, "+", MapIcon.Type.TARGET_X)
            return stack
        }
        return null
    }

}

package me.fzzyhmstrs.ai_odyssey.item

import net.minecraft.block.Block
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import kotlin.math.min

class BullKelpBlockItem(block: Block, settings: Settings): BlockItem(block, settings) {

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        user.air = min(user.maxAir,user.air + 150)
        return super.finishUsing(stack, world, user)
    }


}
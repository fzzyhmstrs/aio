package me.fzzyhmstrs.ai_odyssey.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World


open class HarpoonItem(settings: Settings?) : Item(settings) {

    open fun createArrow(world: World?, stack: ItemStack?, shooter: LivingEntity?): PersistentProjectileEntity? {
        val arrowEntity = ArrowEntity(world, shooter)
        arrowEntity.initFromStack(stack)
        return arrowEntity
    }
}
package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.ai_odyssey.entity.HarpoonEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.Item
import net.minecraft.world.World


open class HarpoonItem(settings: Settings) : Item(settings) {

    open fun createHarpoon(world: World, shooter: LivingEntity): PersistentProjectileEntity {
        return HarpoonEntity(world, shooter)
    }
}
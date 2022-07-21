package me.fzzyhmstrs.ai_odyssey.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.RangedWeaponItem
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.UseAction
import net.minecraft.world.World
import java.util.function.Predicate

class HarpoonLauncherItem(settings: Settings) : RangedWeaponItem(settings) {
 private var attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier>
    
    init {
        val builder = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        builder.put(
            EntityAttributes.GENERIC_ATTACK_SPEED,
            EntityAttributeModifier(
                ATTACK_SPEED_MODIFIER_ID,
                "Tool modifier",
                -2.9,
                EntityAttributeModifier.Operation.ADDITION
            )
        )
        this.attributeModifiers = builder.build()
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
        return ingredient.isOf(Items.IRON_INGOT) && stack.isOf(RegisterItem.HARPOON_LAUNCHER)
    }

    override fun getProjectiles(): Predicate<ItemStack> {
        return Predicate {stack: ItemStack -> stack.item is HarpoonItem}
    }

    override fun getRange(): Int {
        return 15
    }


    override fun getEnchantability(): Int {
        return 1
    }

    override fun getUseAction(stack: ItemStack): UseAction {
        return UseAction.SPEAR
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return if (slot == EquipmentSlot.MAINHAND) {
            this.attributeModifiers
        } else super.getAttributeModifiers(slot)
    }

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
        if (user !is PlayerEntity) {
            return
        }
        if(user.inventory.isEmpty){
          return
        }
        val i = getMaxUseTime(stack) - remainingUseTicks
        if (i < 10) {
            return
        }
        if (!world.isClient) {
            var harpoonStack = ItemStack.EMPTY
            for (slot in 0 until user.inventory.size()){
                val testStack = user.inventory.getStack(slot)
                if (projectiles.test(testStack)){
                    harpoonStack = testStack
                    break
                }
            }
            if (harpoonStack.isEmpty) return
            stack.damage(1, user) { p: PlayerEntity ->
                p.sendToolBreakStatus(
                    user.getActiveHand()
                )
            }
            val harpoonEntity = RegisterItem.HARPOON.createHarpoon(world, user)
            harpoonEntity.setVelocity(
              user,
              user.pitch,
              user.yaw,
              0.0f,
              3.2f,
              0.9f
            )
            val j = EnchantmentHelper.getLevel(Enchantments.POWER, stack)
            if (j > 0) {
                harpoonEntity.damage = harpoonEntity.damage + j.toDouble() * 1.5 + 1.5
            }
            val k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)
            if (k > 0) {
                harpoonEntity.punch = k
            }
            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                harpoonEntity.setOnFireFor(100)
            }

            if (user.abilities.creativeMode) {
              harpoonEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY
            }
            world.spawnEntity(harpoonEntity)
            world.playSoundFromEntity(
              null,
              harpoonEntity,
              SoundEvents.ITEM_TRIDENT_THROW,
              SoundCategory.PLAYERS,
              1.0f,
              0.85f
            )
            if (!user.abilities.creativeMode) {
              user.inventory.removeOne(harpoonStack)
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
    }

}

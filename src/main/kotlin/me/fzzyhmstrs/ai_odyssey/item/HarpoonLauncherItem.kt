package me.fzzyhmstrs.ai_odyssey.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import me.fzzyhmstrs.amethyst_core.item_util.interfaces.Flavorful
import me.fzzyhmstrs.ai_odyssey.entity.LambentTridentEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import net.minecraft.client.item.TooltipContext
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MovementType
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.TridentItem
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.UseAction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class HarpoonLauncherItem(settings: Settings) : TridentItem(settings) {
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
        if(user.inventory.isEmpty()){
          TODO()
        }
        val i = getMaxUseTime(stack) - remainingUseTicks
        if (i < 10) {
            return
        }
        if (!world.isClient) {
            stack.damage(1, user) { p: PlayerEntity ->
                p.sendToolBreakStatus(
                    user.getActiveHand()
                )
            }
              val lambentTridentEntity = LambentTridentEntity(world, user as LivingEntity, stack)
              lambentTridentEntity.setVelocity(
                  user,
                  user.pitch,
                  user.yaw,
                  0.0f,
                  2.5f + j.toFloat() * 0.5f,
                  1.0f
              )
              if (user.abilities.creativeMode) {
                  lambentTridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY
              }
              world.spawnEntity(lambentTridentEntity)
              world.playSoundFromEntity(
                  null,
                  lambentTridentEntity,
                  SoundEvents.ITEM_TRIDENT_THROW,
                  SoundCategory.PLAYERS,
                  1.0f,
                  1.0f
              )
              if (!user.abilities.creativeMode) {
                  user.inventory.removeOne(harpoonStack)
              }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
    }

}

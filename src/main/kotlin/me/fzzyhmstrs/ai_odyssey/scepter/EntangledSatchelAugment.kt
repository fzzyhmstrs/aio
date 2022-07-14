package me.fzzyhmstrs.ai_odyssey.scepter

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.MiscAugment
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.TravelerAugment
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.EnderChestBlock
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.EnderChestInventory
import net.minecraft.item.Items
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import java.util.*

class EntangledSatchelAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MiscAugment(tier,maxLvl, *slot),
    TravelerAugment {

    override fun effect(
        world: World,
        target: Entity?,
        user: LivingEntity,
        level: Int,
        hit: HitResult?,
        effect: AugmentEffect
    ): Boolean {
        if (user !is PlayerEntity) return false
        if (hit != null){
            if (hit.type == HitResult.Type.BLOCK){
                val state = world.getBlockState((hit as BlockHitResult).blockPos)
                if (state.isOf(Blocks.CHEST)){
                    val chestEntity = world.getBlockEntity(hit.blockPos)
                    if (chestEntity != null && chestEntity is ChestBlockEntity){
                        if (chestEntity.isEmpty){
                            val facing = state.get(ChestBlock.FACING)
                            world.removeBlock(hit.blockPos, false)
                            world.setBlockState(hit.blockPos,RegisterBlock.ENTANGLED_CHEST.defaultState.with(ChestBlock.FACING,facing))
                            world.playSound(user,hit.blockPos,SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,SoundCategory.BLOCKS,1.0F, 1.0F)
                        } else {
                            return false
                        }
                    }
                }
            }
        }
        val uuid = user.uuid
        val entangledInventory = getOrCreateEntangledInventory(uuid)
        val optInt = user.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, _: PlayerEntity ->
            GenericContainerScreenHandler.createGeneric9x3(
                syncId,
                inventory,
                entangledInventory
            )
        }, CONTAINER_NAME))
        val bl = optInt.isPresent
        if (bl) {
            user.incrementStat(Stats.OPEN_ENDERCHEST)
        }
        return bl
    }

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.WIT,5,5,5,imbueLevel, LoreTier.LOW_TIER, Items.CHEST)
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER
    }

    companion object{

        private val CONTAINER_NAME: Text = TranslatableText("container.entangled_satchel")

        private val inventories: MutableMap<UUID,EnderChestInventory> = mutableMapOf()

        fun getOrCreateEntangledInventory(uuid: UUID): EnderChestInventory{
            return inventories.getOrPut(uuid) { EnderChestInventory() }
        }

    }
}
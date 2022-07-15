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
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
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
        val entangledInventory = getOrCreateEntangledInventory(user)
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

        private val inventories: MutableMap<UUID,EntangledChestInventory> = mutableMapOf()

        fun getOrCreateEntangledInventory(player: PlayerEntity): EntangledChestInventory{
            return inventories.getOrPut(player.uuid) { EntangledChestInventory(player) }
        }

        fun checkUserIsEntangled(player: PlayerEntity): Boolean{
            return inventories.containsKey(player.uuid)
        }



        class EntangledChestInventory(private val owner: PlayerEntity): SimpleInventory(27) {

            override fun readNbtList(nbtList: NbtList) {
                for (i in 0 until size()){
                    setStack(i, ItemStack.EMPTY)
                }
                for (i in 0 until nbtList.size) {
                    val nbtCompound = nbtList.getCompound(i)
                    val j: Int = (nbtCompound.getByte("Slot").toInt())
                    if (j < 0 || j >= size()) {
                        continue
                    }
                    setStack(j, ItemStack.fromNbt(nbtCompound))
                }
            }

            override fun toNbtList(): NbtList {
                val nbtList = NbtList()
                for (i in 0 until size()) {
                    val itemStack = getStack(i)
                    if (itemStack.isEmpty) continue
                    val nbtCompound = NbtCompound()
                    nbtCompound.putByte("Slot", i.toByte())
                    itemStack.writeNbt(nbtCompound)
                    nbtList.add(nbtCompound)
                }
                return nbtList
            }

            override fun canPlayerUse(player: PlayerEntity): Boolean {
                return player == owner
            }

            override fun onOpen(player: PlayerEntity) {
                player.world.playSound(null,player.blockPos, SoundEvents.BLOCK_ENDER_CHEST_OPEN,SoundCategory.PLAYERS, 0.6F,1.0F)
                super.onOpen(player)
            }

            override fun onClose(player: PlayerEntity) {
                player.world.playSound(null,player.blockPos, SoundEvents.BLOCK_ENDER_CHEST_CLOSE,SoundCategory.PLAYERS, 0.6F,1.0F)
                super.onClose(player)
            }
        }

    }
}
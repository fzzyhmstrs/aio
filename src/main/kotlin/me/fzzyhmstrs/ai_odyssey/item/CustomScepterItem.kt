package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.amethyst_imbuement.AI
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEnchantment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentModifier
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.ScepterAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.util.Nbt
import me.fzzyhmstrs.amethyst_imbuement.util.NbtKeys
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolMaterial
import net.minecraft.loot.function.LootFunction
import net.minecraft.loot.function.SetEnchantmentsLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

open class CustomScepterItem(material: ToolMaterial, settings: Settings, flavor: String = "", startingAugments: List<ScepterAugment> = listOf(), vararg defaultModifier: Identifier): ScepterItem(material, settings, *defaultModifier) {

    constructor(material: ToolMaterial, settings: Settings, startingAugments: List<ScepterAugment> = listOf(), defaultModifiers: List<AugmentModifier> = listOf(), flavor: String = ""):
            this(material,settings,flavor,startingAugments,*modsToIds(defaultModifiers))

    open val defaultAugments: List<ScepterAugment> = startingAugments
    private val ns = AI.MOD_ID
    private val ttn = flavor

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext?) {
        if (ttn != ""){
            tooltip.add(TranslatableText("item.$ns.$ttn.tooltip1").formatted(Formatting.WHITE, Formatting.ITALIC))
        }
        super.appendTooltip(stack, world, tooltip, context)
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        super.inventoryTick(stack, world, entity, slot, selected)
        if (entity !is PlayerEntity) return
        if (world.isClient && selected){
            val rnd = world.random.nextInt(particleChance())
            if (rnd < 1){
                emitParticles(world,entity)
            }
        }
    }

    open fun startingAugments(): LootFunction.Builder{
        var builder = SetEnchantmentsLootFunction.Builder()
            if (defaultAugments.isEmpty()){
                return builder.enchantment(RegisterEnchantment.MAGIC_MISSILE,ConstantLootNumberProvider.create(1.0F))
            } else {
                defaultAugments.forEach {
                    builder = builder.enchantment(it,ConstantLootNumberProvider.create(1.0F))
                }
            }
        return builder
    }

    @Environment(EnvType.CLIENT)
    open fun emitParticles(world: World, entity: PlayerEntity) {

    }

    @Environment(EnvType.CLIENT)
    open fun particleChance(): Int {
        return 10
    }


    companion object{

        fun modsToIds(mods: List<AugmentModifier>): Array<Identifier>{
            val list: MutableList<Identifier> = mutableListOf()
            mods.forEach {
                list.add(it.modifierId)
            }
            return list.toTypedArray()
        }

    }

}
package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.enchantment.ImbuedTouchEnchantment
import me.fzzyhmstrs.ai_odyssey.scepter.*
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentHelper
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.ScepterAugment
import me.fzzyhmstrs.amethyst_imbuement.AI
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterEnchantment {

    private var regEnchant: MutableMap<String, Enchantment> = mutableMapOf()

    //augments
    val IMBUED_TOUCH = ImbuedTouchEnchantment(Enchantment.Rarity.VERY_RARE,1,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND).also{regEnchant["imbued_touch"] = it}

    //scepter spells
    val BARRIER = FullHealAugment(2,3, EquipmentSlot.MAINHAND).also{regEnchant["barrier"] = it}
    val CORRUPTED_SLASH = CorruptedSlashAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["corrupted_slash"] = it}
    val CREATE_TNT = CreateTNTAugment(2,1,Items.TNT,EquipmentSlot.MAINHAND).also{regEnchant["create_tnt"] = it}
    val FLAMING_SLASH = FlamingSlashAugment(1,2, EquipmentSlot.MAINHAND).also{regEnchant["flaming_slash"] = it}
    val FULL_HEAL = FullHealAugment(4,1, EquipmentSlot.MAINHAND).also{regEnchant["full_heal"] = it}
    val GHOSTFORM = GhostformAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["ghostform"] = it}
    val GREATER_REGENERATE = GreaterRegenerateAugment(3,1, EquipmentSlot.MAINHAND).also{regEnchant["greater_regenerate"] = it}
    val RESONATE = ResonateAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["resonate"] = it}
    val SEARING_BLAST = SearingBlastAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["searing_blast"] = it}
    val SMITING_BLOW = SmitingBlowAugment(2,3, EquipmentSlot.MAINHAND).also{regEnchant["smiting_blow"] = it}
    val SOULWAVE = SoulwaveAugment(3,3, EquipmentSlot.MAINHAND).also{regEnchant["soulwave"] = it}
    val VAMPIRIC_SLASH = VampiricSlashAugment(1,2, EquipmentSlot.MAINHAND).also{regEnchant["vampiric_slash"] = it}
    val UNDEATH = UndeathAugment(3,1, EquipmentSlot.MAINHAND).also{regEnchant["undeath"] = it}
    val WEIGHTLESSNESS = WeightlessnessAugment(1,1, EquipmentSlot.MAINHAND).also{regEnchant["weightlessness"] = it}

    fun registerAll(){

        for (k in regEnchant.keys){
            val enchant = regEnchant[k]
            Registry.register(Registry.ENCHANTMENT, Identifier(AI.MOD_ID, k), enchant)
            if (enchant is ScepterAugment){
                AugmentHelper.registerAugmentStat(enchant)
            }
        }

        regEnchant.clear()
    }

}
package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.augment.EnragedAugment
import me.fzzyhmstrs.ai_odyssey.augment.ImbuedTouchAugment
import me.fzzyhmstrs.ai_odyssey.augment.PressureResistanceAugment
import me.fzzyhmstrs.ai_odyssey.scepter.*
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentHelper
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.ScepterAugment
import me.fzzyhmstrs.amethyst_imbuement.AI
import net.minecraft.enchantment.DamageEnchantment
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterEnchantment {

    private var regEnchant: MutableMap<String, Enchantment> = mutableMapOf()

    //vanilla enchants
    val HYDROPHOBIC = DamageEnchantment(Enchantment.Rarity.UNCOMMON, 4, EquipmentSlot.MAINHAND).also{regEnchant["hydrophobic"] = it}
    
    //augments
    val ENRAGED = EnragedAugment(Enchantment.Rarity.VERY_RARE,1,EquipmentSlot.MAINHAND).also{regEnchant["enraged"] = it}
    val IMBUED_TOUCH = ImbuedTouchAugment(Enchantment.Rarity.VERY_RARE,1,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND).also{regEnchant["imbued_touch"] = it}
    val PRESSURE_RESISTANCE = PressureResistanceAugment(Enchantment.Rarity.VERY_RARE,5, *AI.slots).also{regEnchant["pressure_resistance"] = it}

    //scepter spells
    val BARRIER = BarrierAugment(2,3, EquipmentSlot.MAINHAND).also{regEnchant["barrier"] = it}
    val BLESSING_OF_THE_DEEP = BlessingOfTheDeepAugment(3,3, EquipmentSlot.MAINHAND).also{regEnchant["blessing_of_the_deep"] = it}
    val CORNUCOPIA = CornucopiaAugment(3,3, EquipmentSlot.MAINHAND).also{regEnchant["cornucopia"] = it}
    val CORRUPTED_SLASH = CorruptedSlashAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["corrupted_slash"] = it}
    val CREATE_TNT = CreateTNTAugment(2,1,Items.TNT,EquipmentSlot.MAINHAND).also{regEnchant["create_tnt"] = it}
    val DETERMINATION = DeterminationAugment(1,3, EquipmentSlot.MAINHAND).also{regEnchant["determination"] = it}
    val ENFEEBLING_BOLT = EnfeeblingboltAugment(1,3, EquipmentSlot.MAINHAND).also{regEnchant["enfeebling_bolt"] = it}
    val ENTANGLED_SATCHEL = EntangledSatchelAugment(2,1, EquipmentSlot.MAINHAND).also{regEnchant["entangled_satchel"] = it}
    val FLAMING_SLASH = FlamingSlashAugment(1,2, EquipmentSlot.MAINHAND).also{regEnchant["flaming_slash"] = it}
    val FULL_HEAL = FullHealAugment(4,1, EquipmentSlot.MAINHAND).also{regEnchant["full_heal"] = it}
    val GHOSTFORM = GhostformAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["ghostform"] = it}
    val GREATER_REGENERATE = GreaterRegenerateAugment(3,1, EquipmentSlot.MAINHAND).also{regEnchant["greater_regenerate"] = it}
    val PLACE_BED = PlaceBedAugment(1,1,Items.RED_BED,EquipmentSlot.MAINHAND).also{regEnchant["place_bed"] = it}
    val RESONATE = ResonateAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["resonate"] = it}
    val SEARING_BLAST = SearingBlastAugment(2,2, EquipmentSlot.MAINHAND).also{regEnchant["searing_blast"] = it}
    val SMITING_BLOW = SmitingBlowAugment(2,3, EquipmentSlot.MAINHAND).also{regEnchant["smiting_blow"] = it}
    val SOULWAVE = SoulwaveAugment(3,3, EquipmentSlot.MAINHAND).also{regEnchant["soulwave"] = it}
    val VAMPIRIC_BOLT = VampiricBoltAugment(1,3, EquipmentSlot.MAINHAND).also{regEnchant["vampiric_bolt"] = it}
    val VAMPIRIC_SLASH = VampiricSlashAugment(1,2, EquipmentSlot.MAINHAND).also{regEnchant["vampiric_slash"] = it}
    val UNDEATH = UndeathAugment(3,1, EquipmentSlot.MAINHAND).also{regEnchant["undeath"] = it}
    val WEIGHTLESSNESS = WeightlessnessAugment(1,1, EquipmentSlot.MAINHAND).also{regEnchant["weightlessness"] = it}

    fun registerAll(){

        for (k in regEnchant.keys){
            val enchant = regEnchant[k]
            Registry.register(Registry.ENCHANTMENT, Identifier(AIO.MOD_ID, k), enchant)
            if (enchant is ScepterAugment){
                AugmentHelper.registerAugmentStat(enchant)
            }
        }

        regEnchant.clear()
    }

}

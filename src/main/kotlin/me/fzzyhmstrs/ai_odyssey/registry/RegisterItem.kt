@file:Suppress("MemberVisibilityCanBePrivate")

package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.item.*
import me.fzzyhmstrs.ai_odyssey.tool.BloodWitchToolMaterial
import me.fzzyhmstrs.ai_odyssey.tool.ScepterLvl4ToolMaterial
import me.fzzyhmstrs.ai_odyssey.tool.ScepterOfBladesToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.item.CustomFlavorItem
import me.fzzyhmstrs.amethyst_imbuement.item.ScepterItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEnchantment
import me.fzzyhmstrs.amethyst_imbuement.tool.ScepterLvl1ToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.tool.ScepterLvl2ToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.tool.ScepterLvl3ToolMaterial
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment as RegisterEnchantmentAIO
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterModifier as RegisterModifierAI


object RegisterItem {

    private val regItem: MutableMap<String, Item> = mutableMapOf()

    //materials and crafting items
    val ALEXANDRITE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["alexandrite"] = it}
    val BLOODSTONE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["bloodstone"] = it}
    val SERPENTINE = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)).also{ regItem["serpentine"] = it}
    val MYSTIC_FRAGMENT = CustomFlavorItem(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC),"mystic_fragment",true).also{ regItem["mystic_fragment"] = it}
    val MYSTIC_QUINTESSENCE = CustomFlavorItem(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC),"mystic_quintessence",true).also{ regItem["mystic_quintessence"] = it}

    //tools and weapons
    val RESPLENDENT_SCEPTER = ScepterItem(ScepterLvl4ToolMaterial, FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        RegisterModifier.RESPLENDENT.modifierId).also{ regItem["resplendent_scepter"] = it}
    val FURIOUS_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(RegisterEnchantment.SOUL_MISSILE),
        listOf(RegisterModifier.FURIOUS)
    ).also{ regItem["furious_scepter"] = it}
    val WITTY_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(RegisterEnchantment.SHINE),
        listOf(RegisterModifier.WITTY)
    ).also{ regItem["witty_scepter"] = it}
    val GRACEFUL_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(RegisterEnchantment.REGENERATE),
        listOf(RegisterModifier.GRACEFUL)
    ).also{ regItem["graceful_scepter"] = it}
    val BLAZING_SCEPTER = BlazingScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.FLAMEBOLT, RegisterEnchantment.FIREBALL),
        listOf(RegisterModifier.FIRE_ASPECT, RegisterModifierAI.LESSER_THRIFTY)
    ).also{ regItem["blazing_scepter"] = it}
    val SPARKING_SCEPTER = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.LIGHTNING_BOLT),
        listOf(RegisterModifier.LIGHTNING_ASPECT, RegisterModifierAI.LESSER_THRIFTY)
    ).also{ regItem["sparking_scepter"] = it}
    val SCEPTER_OF_BLADES = CustomScepterItem(ScepterOfBladesToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.SPECTRAL_SLASH),
        listOf(RegisterModifier.BLADE_ASPECT, RegisterModifierAI.LESSER_THRIFTY)
    ).also{ regItem["scepter_of_blades"] = it}
    val CLERICS_SCEPTER = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.MINOR_HEAL,RegisterEnchantment.FORTIFY),
        listOf(RegisterModifier.HEALING, RegisterModifier.GRACEFUL)
    ).also{ regItem["clerics_scepter"] = it}
    val SCEPTER_OF_SUMMONING = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.SUMMON_BOAT,RegisterEnchantment.SUMMON_ZOMBIE),
        listOf(RegisterModifier.SUMMONERS_ASPECT, RegisterModifier.WITTY)
    ).also{ regItem["scepter_of_Summoning"] = it}
    val SCEPTER_OF_DEPTHS = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.CREATE_WATER,RegisterEnchantment.CREATE_SPONGE, RegisterEnchantmentAIO.SEARING_BLAST),
        listOf(RegisterModifier.OCEANIC)
    ).also{ regItem["scepter_of_depths"] = it}
    val BUILDERS_SCEPTER = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.HARD_LIGHT_BRIDGE),
        listOf(RegisterModifier.BUILDERS_ASPECT,RegisterModifier.WITTY)
    ).also{ regItem["builders_scepter"] = it}
    val SCEPTER_OF_THE_PALADIN = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantmentAIO.SMITING_BLOW,RegisterEnchantment.FORCE_FIELD),
        listOf(RegisterModifier.PROTECTIVE,RegisterModifier.GRACEFUL,RegisterModifier.SMITING)
    ).also{ regItem["scepter_of_the_paladin"] = it}
    val SCEPTER_OF_THE_PALADIN_2 = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantmentAIO.SMITING_BLOW,RegisterEnchantment.FORTIFY),
        listOf(RegisterModifier.PROTECTIVE,RegisterModifier.GRACEFUL,RegisterModifier.ENDURING)
    ).also{ regItem["scepter_of_the_paladin_2"] = it}
    val CORRUPTED_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(RegisterEnchantmentAIO.CORRUPTED_SLASH),
        listOf(RegisterModifier.NECROTIC)
    ).also{ regItem["corrupted_scepter"] = it}
    val ATTUNED_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(),
        listOf(RegisterModifierAI.ATTUNED)
    ).also{ regItem["attuned_scepter"] = it}
    val THRIFTY_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(),
        listOf(RegisterModifierAI.THRIFTY)
    ).also{ regItem["thrifty_scepter"] = it}
    val SKILLFUL_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(),
        listOf(RegisterModifier.SKILLFUL)
    ).also{ regItem["skillful_scepter"] = it}
    val ENDURING_SCEPTER = CustomScepterItem(ScepterLvl1ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON),
        listOf(),
        listOf(RegisterModifier.ENDURING)
    ).also{ regItem["enduring_scepter"] = it}
    val SCEPTER_OF_INSIGHT = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.SOUL_MISSILE),
        listOf(RegisterModifier.INSIGHTFUL,RegisterModifier.FURIOUS)
    ).also{ regItem["scepter_of_insight"] = it}
    val CARTOGRAPHERS_TRANSIT = CustomScepterItem(ScepterLvl2ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE),
        listOf(RegisterEnchantment.SUMMON_BOAT, RegisterEnchantment.SUMMON_STRIDER),
        listOf(RegisterModifier.TRAVELER)
    ).also{ regItem["cartographers_transit"] = it}
    val ETERNITY = CustomScepterItem(ScepterLvl3ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(RegisterEnchantmentAIO.UNDEATH),
        listOf(RegisterModifier.NECROTIC,RegisterModifier.SUMMONERS_ASPECT, RegisterModifier.DEMANDING)
    ).also{ regItem["eternity"] = it}
    val LETHALITY = LethalityScepterItem(ScepterLvl3ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(RegisterEnchantment.FANGS),
        listOf(RegisterModifier.DANGEROUS,RegisterModifier.FURIOUS, RegisterModifierAI.GREATER_THRIFTY)
    ).also{ regItem["lethality"] = it}
    val RESONANCE = CustomScepterItem(ScepterLvl3ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(RegisterEnchantmentAIO.RESONATE),
        listOf(RegisterModifier.ECHOING, RegisterModifier.SKILLFUL)
    ).also{ regItem["resonance"] = it}
    val REDEMPTION = CustomScepterItem(ScepterLvl3ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(),
        listOf(RegisterModifier.HEALERS_PACT,RegisterModifier.HEALERS_GRACE, RegisterModifierAI.GREATER_THRIFTY, RegisterModifier.GRACEFUL)
    ).also{ regItem["redemption"] = it}
    val SPITE_OF_THE_BLOOD_WITCH = SpiteOfTheBloodWitchScepterItem(BloodWitchToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(),
        listOf(RegisterModifier.BLOOD_PACT,RegisterModifier.BLOOD_MAGIC, RegisterModifierAI.GREATER_ATTUNED, RegisterModifier.GREATER_ENDURING, RegisterModifier.FURIOUS)
    ).also{ regItem["spite_of_the_blood_witch"] = it}
    val WAND_OF_THE_MIND_MAGE = CustomScepterItem(ScepterLvl3ToolMaterial,FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC),
        listOf(),
        listOf(RegisterModifier.KNOWLEDGE_PACT, RegisterModifier.MIND_MAGIC, RegisterModifierAI.GREATER_THRIFTY, RegisterModifier.GREATER_REACH, RegisterModifier.WITTY)
    ).also{ regItem["wand_of_the_mind_mage"] = it}
    //flora and fauna


    //food and other practical items
    private val seaAppleFood = FoodComponent.Builder().hunger(5).saturationModifier(0.4f).build()
    val SEA_APPLE = AliasedBlockItem(RegisterBlock.SEA_APPLE_BUSH,FabricItemSettings().group(ItemGroup.FOOD).food(seaAppleFood)).also{ regItem["sea_apple"] = it}
    private val bullKelpFood = FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build()
    val BULL_KELP = BullKelpBlockItem(RegisterBlock.BULL_KELP, FabricItemSettings().group(ItemGroup.DECORATIONS).food(bullKelpFood)).also{ regItem["bull_kelp"] = it}



    //trinkets and baubles
    val BOOK_OF_LEGEND = BookOfLegendItem(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC),"book_of_legend",true).also{ regItem["book_of_legend"] = it}
    val DIVINE_CORONET = DivineCoronetItem(FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC),"divine_coronet").also{ regItem["divine_coronet"] = it}
    val FACILITY_CONFIGURATOR = FacilityConfigurationStick(FabricItemSettings().group(ItemGroup.MISC)).also{ regItem["facility_configurator"] = it}
    val RESPLENDENT_RARITY = Item(FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC)).also{ regItem["resplendent_rarity"] = it}
    val STRANGE_MAP = StrangeMapItem(FabricItemSettings().group(ItemGroup.MISC)).also{ regItem["strange_map"] = it}
    val STRANGE_SCRAP = CustomFlavorItem(FabricItemSettings().group(ItemGroup.MISC),"strange_scrap",false).also{ regItem["strange_scrap"] = it}

    fun registerAll() {
        for (k in regItem.keys){
            Registry.register(Registry.ITEM,Identifier(AIO.MOD_ID,k), regItem[k])
        }
        regItem.clear()
    }
}
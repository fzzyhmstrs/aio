package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.modifier.ModifierConsumers
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterModifier
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.AugmentModifier
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.util.Identifier

object RegisterModifier {

    private val regMod: MutableList<AugmentModifier> = mutableListOf()
    val FURIOUS = AugmentModifier(Identifier(AIO.MOD_ID,"furious")).withXpMod(SpellType.FURY,1).withDamage(0.25F).also { regMod.add(it) }
    val WITTY = AugmentModifier(Identifier(AIO.MOD_ID,"witty")).withXpMod(SpellType.WIT,1).withRange(0.25).also { regMod.add(it) }
    val GRACEFUL = AugmentModifier(Identifier(AIO.MOD_ID,"graceful")).withXpMod(SpellType.GRACE,1).withDuration(durationPercent = 5).also { regMod.add(it) }
    val DEMANDING = AugmentModifier(Identifier(AIO.MOD_ID,"demanding"), manaCostModifier = 0.15) .also { regMod.add(it) }
    val NECROTIC = AugmentModifier(Identifier(AIO.MOD_ID,"necrotic")).withConsumer(ModifierConsumers.NECROTIC_CONSUMER).also { regMod.add(it) }
    val HEALING = AugmentModifier(Identifier(AIO.MOD_ID,"healing")).withConsumer(ModifierConsumers.HEALING_CONSUMER).also { regMod.add(it) }
    val SMITING = AugmentModifier(Identifier(AIO.MOD_ID,"smiting")).withConsumer(ModifierConsumers.SMITING_CONSUMER).also { regMod.add(it) }
    val INSIGHTFUL = AugmentModifier(Identifier(AIO.MOD_ID,"insightful")).withConsumer(ModifierConsumers.INSIGHTFUL_CONSUMER).also { regMod.add(it) }
    val DANGEROUS = AugmentModifier(Identifier(AIO.MOD_ID,"dangerous")).withDamage(0.0F,0.0F,20.0F).also { regMod.add(it) }

    fun registerAll(){
        regMod.forEach {
            RegisterModifier.ENTRIES.register(it)
        }
        regMod.clear()
    }

}
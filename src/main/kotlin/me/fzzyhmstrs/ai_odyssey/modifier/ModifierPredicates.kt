package me.fzzyhmstrs.ai_odyssey.modifier

import me.fzzyhmstrs.amethyst_imbuement.scepter.SpectralSlashAugment
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.*
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Predicate

object ModifierPredicates {

    val FURIOUS_PREDICATE = Predicate {id: Identifier -> furiousPredicate(id)}
    private fun furiousPredicate(id: Identifier): Boolean{
        return ScepterObject.getAugmentType(id.toString()) == SpellType.FURY
    }
    val WITTY_PREDICATE = Predicate {id: Identifier -> wittyPredicate(id)}
    private fun wittyPredicate(id: Identifier): Boolean{
        return ScepterObject.getAugmentType(id.toString()) == SpellType.WIT
    }
    val GRACEFUL_PREDICATE = Predicate {id: Identifier -> gracefulPredicate(id)}
    private fun gracefulPredicate(id: Identifier): Boolean{
        return ScepterObject.getAugmentType(id.toString()) == SpellType.GRACE
    }

    val BLADE_PREDICATE = Predicate {id: Identifier -> bladePredicate(id)}
    private fun bladePredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is SpectralSlashAugment
    }

    val FIRE_PREDICATE = Predicate {id: Identifier -> firePredicate(id)}
    private fun firePredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is FireAugment
    }

    val LIGHTNING_PREDICATE = Predicate {id: Identifier -> lightningPredicate(id)}
    private fun lightningPredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is FireAugment
    }

    val SUMMONERS_PREDICATE = Predicate {id: Identifier -> summonersPredicate(id)}
    private fun summonersPredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is SummonEntityAugment
    }

    val BUILDERS_PREDICATE = Predicate {id: Identifier -> buildersPredicate(id)}
    private fun buildersPredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is BuilderAugment
    }

    val OCEANIC_PREDICATE = Predicate {id: Identifier -> oceanicPredicate(id)}
    private fun oceanicPredicate(id: Identifier): Boolean{
        val augment = Registry.ENCHANTMENT.get(id)?:return false
        return augment is OceanicAugment
    }

}
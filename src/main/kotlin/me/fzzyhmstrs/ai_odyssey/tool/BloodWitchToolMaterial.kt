package me.fzzyhmstrs.ai_odyssey.tool

import me.fzzyhmstrs.ai_odyssey.config.AioConfig
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.config.AiConfig
import net.minecraft.recipe.Ingredient
import kotlin.math.max


object BloodWitchToolMaterial: ScepterToolMaterial() {
    override fun getDurability(): Int {
        return AioConfig.scepters.bloodWitchDurability
    }
    fun defaultDurability(): Int{
        return 32
    }
    override fun getMiningSpeedMultiplier(): Float {
        return 1.0f
    }
    override fun getAttackDamage(): Float {
        return 2.0f
    }
    override fun getMiningLevel(): Int {
        return 1
    }
    override fun getEnchantability(): Int {
        return 35
    }
    override fun getRepairIngredient(): Ingredient {
        return Ingredient.ofItems(RegisterItem.BLOODSTONE)
    }
    override fun healCooldown(): Long {
        return max(AiConfig.scepters.baseRegenRateTicks,minCooldown())
    }
    override fun scepterTier(): Int{
        return 3
    }
}

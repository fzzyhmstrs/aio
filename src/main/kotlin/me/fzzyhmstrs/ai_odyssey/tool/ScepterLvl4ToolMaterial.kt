package me.fzzyhmstrs.ai_odyssey.tool

import me.fzzyhmstrs.ai_odyssey.config.AioConfig
import me.fzzyhmstrs.ai_odyssey.registry.RegisterItem
import me.fzzyhmstrs.amethyst_imbuement.config.AiConfig
import me.fzzyhmstrs.amethyst_imbuement.tool.ScepterMaterialAddon
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import kotlin.math.max

object ScepterLvl4ToolMaterial: ToolMaterial, ScepterMaterialAddon {
    override fun getDurability(): Int {
        return AioConfig.scepters.resplendentDurability
    }
    fun defaultDurability(): Int{
        return 2940
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
        return 30
    }
    override fun getRepairIngredient(): Ingredient {
        return Ingredient.ofItems(RegisterItem.MYSTIC_FRAGMENT)
    }
    override fun healCooldown(): Long {
        return max(AiConfig.scepters.baseRegenRateTicks - 90L, minCooldown())
    }
    override fun scepterTier(): Int{
        return 4
    }

}
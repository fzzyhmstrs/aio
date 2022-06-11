package me.fzzyhmstrs.ai_odyssey.config

import me.fzzyhmstrs.ai_odyssey.tool.ScepterLvl4ToolMaterial
import me.fzzyhmstrs.ai_odyssey.tool.ScepterOfBladesToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.config.AiConfig

object AioConfig {

    const val augmentVersion = "_v0"

    var scepters: Scepters

    init{
        scepters = AiConfig.readOrCreate("scepters_v0.json") { Scepters() }
    }

    fun initConfig(){}

    class Scepters {
        var resplendentDurability: Int = ScepterLvl4ToolMaterial.defaultDurability()
        var bladesDurability: Int = ScepterOfBladesToolMaterial.defaultDurability()
        var bladesDamage: Float = ScepterOfBladesToolMaterial.defaultAttackDamage()
    }

}
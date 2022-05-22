package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.KelpPlantBlock

class SeaBambooPlantBlock(settings: Settings): KelpPlantBlock(settings) {

    override fun getStem(): AbstractPlantStemBlock {
        return RegisterBlock.SEA_BAMBOO
    }
}
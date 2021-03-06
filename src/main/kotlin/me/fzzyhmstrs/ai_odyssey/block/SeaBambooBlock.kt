package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block
import net.minecraft.block.KelpBlock

class SeaBambooBlock(settings: Settings): KelpBlock(settings) {

    override fun getPlant(): Block {
        return RegisterBlock.SEA_BAMBOO_PLANT
    }

}
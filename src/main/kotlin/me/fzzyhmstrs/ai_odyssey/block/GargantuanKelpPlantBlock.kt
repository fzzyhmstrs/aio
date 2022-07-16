package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.Block

class GargantuanKelpPlantBlock(settings: Settings): AbstractGargantuanKelpPlantBlock(settings) {

    override fun getStreamer(): AbstractGargantuanKelpStreamerBlock {
        return RegisterBlock.GARGANTUAN_KELP_STREAMER
    }

    override fun getStem(): AbstractPlantStemBlock {
        return RegisterBlock.GARGANTUAN_KELP
    }

    override fun getPlant(): Block {
        return this
    }
}
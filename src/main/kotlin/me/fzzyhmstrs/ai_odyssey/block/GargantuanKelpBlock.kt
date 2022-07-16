package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block
import net.minecraft.block.PillarBlock

class GargantuanKelpBlock(settings: Settings): AbstractGargantuanKelpBlock(settings) {

    override fun getStreamer(): AbstractGargantuanKelpStreamerBlock {
        return RegisterBlock.GARGANTUAN_KELP_STREAMER
    }

    override fun getWood(): PillarBlock {
        return RegisterBlock.GARGANTUAN_KELP_STEM
    }

    override fun getPlant(): Block {
        return RegisterBlock.GARGANTUAN_KELP_PLANT
    }
}
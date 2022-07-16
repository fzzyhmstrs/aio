package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.Block

class GargantuanKelpStreamerBlock(settings: Settings): AbstractGargantuanKelpStreamerBlock(settings) {

    override fun getStreamer(): AbstractGargantuanKelpStreamerBlock {
        return this
    }

    override fun getStem(): AbstractGargantuanKelpBlock {
        return RegisterBlock.GARGANTUAN_KELP
    }

    override fun getPlant(): AbstractGargantuanKelpPlantBlock {
        return RegisterBlock.GARGANTUAN_KELP_PLANT
    }
}
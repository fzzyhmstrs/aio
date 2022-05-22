package me.fzzyhmstrs.ai_odyssey.registry

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object RegisterRenderer {

    fun registerAll(){
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_STREAMER, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.TURTLE_GRASS, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.WIRE_WEED, RenderLayer.getCutout())
    }
}
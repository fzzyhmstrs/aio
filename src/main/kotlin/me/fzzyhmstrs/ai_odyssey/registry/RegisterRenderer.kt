package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.model.CrystallineItemLockBlockEntityRenderer
import me.fzzyhmstrs.amethyst_core.entity_util.MissileEntityRenderer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory

object RegisterRenderer {


    fun registerAll(){

        EntityRendererRegistry.register(
            RegisterEntity.ENFEEBLING_BOLT_ENTITY
        ){context: EntityRendererFactory.Context ->
            MissileEntityRenderer(
                context,
                0.2f,
                0.2f,
                0.2f,
                0.5F,
                1.5F
            )
        }


        BlockEntityRendererRegistry.register(RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY
        ){context: BlockEntityRendererFactory.Context ->
           CrystallineItemLockBlockEntityRenderer(
                context
            )
        }

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_STREAMER, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.CRYSTALLINE_NUM_LOCK, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.MIDNIGHT_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.MIDNIGHT_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_APPLE_BUSH, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.TURTLE_GRASS, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.WIRE_WEED, RenderLayer.getCutout())

        
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.HARD_LIGHT_BARRIER, RenderLayer.getTranslucent())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.CRYSTALLINE_SWITCH, RenderLayer.getTranslucent())
    }
}

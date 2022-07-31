package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.model.CrystallineItemLockBlockEntityRenderer
import me.fzzyhmstrs.ai_odyssey.model.HarpoonEntityRenderer
import me.fzzyhmstrs.ai_odyssey.model.LambentTridentEntityModel
import me.fzzyhmstrs.ai_odyssey.model.LambentTridentEntityRenderer
import me.fzzyhmstrs.amethyst_core.entity_util.MissileEntityRenderer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

object RegisterRenderer {
    val LAMBENT_TRIDENT: EntityModelLayer = EntityModelLayer(Identifier(AIO.MOD_ID,"lambent_trident"),"lambent_trident_model")

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

        EntityRendererRegistry.register(
            RegisterEntity.VAMPIRIC_BOLT_ENTITY
        ){context: EntityRendererFactory.Context ->
            MissileEntityRenderer(
                context,
                0.0f,
                0.0f,
                0.0f,
                0.5F,
                1.5F
            )
        }
        
        EntityRendererRegistry.register(
            RegisterEntity.LAMBENT_TRIDENT_ENTITY
        ){context: EntityRendererFactory.Context ->
            LambentTridentEntityRenderer(
                context
            )
        }

        EntityRendererRegistry.register(
            RegisterEntity.HAPROON_ENTITY
        ){context: EntityRendererFactory.Context ->
            HarpoonEntityRenderer(
                context
            )
        }


        BlockEntityRendererRegistry.register(RegisterEntity.CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY
        ){context: BlockEntityRendererFactory.Context ->
           CrystallineItemLockBlockEntityRenderer(
                context
            )
        }
        
        EntityModelLayerRegistry.registerModelLayer(LAMBENT_TRIDENT, LambentTridentEntityModel::getTexturedModelData)
        
        ModelPredicateProviderRegistry.register(
            RegisterItem.LAMBENT_TRIDENT, Identifier("throwing")
        ) { stack: ItemStack, _: ClientWorld?, entity: LivingEntity?, _: Int -> if (entity != null && entity.isUsingItem && entity.activeItem == stack) 1.0f else 0.0f }

        ModelPredicateProviderRegistry.register(
            RegisterItem.HARPOON_LAUNCHER, Identifier("throwing")
        ) { stack: ItemStack, _: ClientWorld?, entity: LivingEntity?, _: Int -> if (entity != null && entity.isUsingItem && entity.activeItem == stack) 1.0f else 0.0f }


        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.BULL_KELP_STREAMER, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.CRYSTALLINE_NUM_LOCK, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.MIDNIGHT_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.MIDNIGHT_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_APPLE_BUSH, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SEA_BAMBOO_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SKELETAL_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.SKELETAL_KELP_PLANT, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_KELP_DOOR, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_KELP_STREAMER, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_KELP_TRAPDOOR, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_SKELETAL_KELP, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_SKELETAL_KELP_DOOR, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_SKELETAL_KELP_STREAMER, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.GARGANTUAN_SKELETAL_KELP_TRAPDOOR, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.TURTLE_GRASS, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.WIRE_WEED, RenderLayer.getCutout())

        
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.HARD_LIGHT_BARRIER, RenderLayer.getTranslucent())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.CRYSTALLINE_SWITCH, RenderLayer.getTranslucent())
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlock.IMBUED_GLASS, RenderLayer.getTranslucent())
    }
}

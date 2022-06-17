@file:Suppress("UNUSED_PARAMETER")

package me.fzzyhmstrs.ai_odyssey.model

import me.fzzyhmstrs.ai_odyssey.entity.CrystallineItemLockBlockEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f


@Suppress("DEPRECATION")
@Environment(value = EnvType.CLIENT)
class CrystallineItemLockBlockEntityRenderer(ctx: BlockEntityRendererFactory.Context) :
    BlockEntityRenderer<CrystallineItemLockBlockEntity> {

    override fun render(
        crystallineItemLockBlockEntity: CrystallineItemLockBlockEntity,
        f: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int,
        j: Int
    ) {
        matrixStack.push()
        matrixStack.scale(0.5F,0.5F,0.5F)
        matrixStack.translate(1.0, 1.9, 1.0)
        //matrixStack.translate(0.0, (0.1f + MathHelper.sin(g * 0.1f) * 0.01f).toDouble(), 0.0)

        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F))

        val lightAbove = WorldRenderer.getLightmapCoordinates(crystallineItemLockBlockEntity.world, crystallineItemLockBlockEntity.pos.up())
        MinecraftClient.getInstance().itemRenderer.renderItem(
            crystallineItemLockBlockEntity.heldItemStack,
            ModelTransformation.Mode.FIXED,
            lightAbove,
            j,
            matrixStack,
            vertexConsumerProvider,
            crystallineItemLockBlockEntity.pos.asLong().toInt()
        )
        matrixStack.pop()

    }
}
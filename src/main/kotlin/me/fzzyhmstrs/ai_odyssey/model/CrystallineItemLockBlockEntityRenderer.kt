@file:Suppress("UNUSED_PARAMETER")

package me.fzzyhmstrs.ai_odyssey.model

import me.fzzyhmstrs.ai_odyssey.block.AbstractLockBlock
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
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

@Suppress("DEPRECATION")
@Environment(value = EnvType.CLIENT)
class CrystallineItemLockBlockEntityRenderer(val ctx: BlockEntityRendererFactory.Context) :
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
        when (crystallineItemLockBlockEntity.cachedState.get(AbstractLockBlock.FACING)){
            Direction.NORTH->{
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(67.5F))
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F))
            }
            Direction.EAST->{
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(67.5F))
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180F))
            }
            Direction.SOUTH->{
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(67.5F))
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270F))
            }
            Direction.WEST->{
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(67.5F))
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0F))
            }
            else -> {
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(67.5F))
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F))
            }
        }
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
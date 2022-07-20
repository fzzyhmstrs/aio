package me.fzzyhmstrs.ai_odyssey.model

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.entity.HarpoonEntity
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.ProjectileEntityRenderer
import net.minecraft.util.Identifier

class HarpoonEntityRenderer(context: EntityRendererFactory.Context): ProjectileEntityRenderer<HarpoonEntity>(context) {
    private val TEXTURE = Identifier(AIO.MOD_ID,"textures/entity/projectiles/arrow.png")

    override fun getTexture(entity: HarpoonEntity?): Identifier {
        return TEXTURE
    }
}
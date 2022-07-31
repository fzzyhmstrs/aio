package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.Block
import net.minecraft.block.PillarBlock
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes

class GargantuanSkeletalKelpPlantBlock(settings: Settings): AbstractGargantuanKelpPlantBlock(settings, Direction.UP, VoxelShapes.fullCube()) {

    override fun getStreamer(): AbstractGargantuanKelpStreamerBlock {
        return RegisterBlock.GARGANTUAN_SKELETAL_KELP_STREAMER
    }

    override fun getStem(): AbstractGargantuanKelpBlock {
        return RegisterBlock.GARGANTUAN_SKELETAL_KELP
    }

    override fun getWood(): PillarBlock {
        return RegisterBlock.GARGANTUAN_SKELETAL_KELP_STEM
    }

    override fun getPlant(): AbstractGargantuanKelpPlantBlock {
        return this
    }
}
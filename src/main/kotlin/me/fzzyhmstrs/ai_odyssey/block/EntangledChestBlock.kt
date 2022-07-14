package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.AbstractChestBlock
import net.minecraft.block.BlockState
import net.minecraft.block.DoubleBlockProperties
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.function.Supplier

class EntangledChestBlock(settings: Settings, supplier: Supplier<BlockEntityType<out ChestBlockEntity>>): AbstractChestBlock<ChestBlockEntity>(settings,supplier) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        TODO("Not yet implemented")
    }

    override fun getBlockEntitySource(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        ignoreBlocked: Boolean
    ): DoubleBlockProperties.PropertySource<out ChestBlockEntity> {
        TODO("Not yet implemented")
    }
}
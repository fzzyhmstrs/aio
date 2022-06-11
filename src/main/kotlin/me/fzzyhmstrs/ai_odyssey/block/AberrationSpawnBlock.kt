package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.entity.AberrationSpawnBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEntity
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class AberrationSpawnBlock(settings: Settings): BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AberrationSpawnBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if(!world.isClient) checkType(
            type, RegisterEntity.ABERRATION_SPAWN_BLOCK_ENTITY
        ) { world2: World, pos: BlockPos, state2: BlockState, blockEntity: AberrationSpawnBlockEntity ->
            AberrationSpawnBlockEntity.tick(
                world2,
                pos,
                state2,
                blockEntity
            )
        } else null
    }

}
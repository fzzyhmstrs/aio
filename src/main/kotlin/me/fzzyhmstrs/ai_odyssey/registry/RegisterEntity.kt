package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineItemLockBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineNumLockBlockEntity
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry

object RegisterEntity {

    val CRYSTALLINE_SWITCH_BLOCK_ENTITY: BlockEntityType<CrystallineSwitchBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":crystalline_switch_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            CrystallineSwitchBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.CRYSTALLINE_SWITCH).build(null))

    val CRYSTALLINE_NUM_LOCK_BLOCK_ENTITY: BlockEntityType<CrystallineNumLockBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":crystalline_switch_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            CrystallineNumLockBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.CRYSTALLINE_NUM_LOCK).build(null))

    val CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY: BlockEntityType<CrystallineItemLockBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":crystalline_switch_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            CrystallineItemLockBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.CRYSTALLINE_ITEM_LOCK).build(null))

    fun registerAll(){

    }
}
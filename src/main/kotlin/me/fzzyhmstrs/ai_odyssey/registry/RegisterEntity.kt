package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.entity.CrystallineSwitchBlockEntity
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterBlock
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
        }, RegisterBlock.FORCEFIELD_BLOCK).build(null))

    fun registerAll(){

    }
}
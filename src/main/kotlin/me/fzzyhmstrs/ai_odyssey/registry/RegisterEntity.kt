package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.entity.*
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

object RegisterEntity {

    val ABERRATION_SPAWN_BLOCK_ENTITY: BlockEntityType<AberrationSpawnBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":aberration_spawn_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            AberrationSpawnBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.ABERRATION_SPAWN).build(null))

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
        AIO.MOD_ID + ":crystalline_num_lock_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            CrystallineNumLockBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.CRYSTALLINE_NUM_LOCK).build(null))

    val CRYSTALLINE_ITEM_LOCK_BLOCK_ENTITY: BlockEntityType<CrystallineItemLockBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":crystalline_item_lock_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            CrystallineItemLockBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.CRYSTALLINE_ITEM_LOCK).build(null))

    val FACILITY_TELEPORTER_BLOCK_ENTITY: BlockEntityType<FacilityTeleporterBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":facility_teleporter_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            FacilityTeleporterBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.FACILITY_TELEPORTER).build(null))

    val MYSTERIOUS_PORTAL_FRAME_BLOCK_ENTITY: BlockEntityType<MysteriousPortalFrameBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        AIO.MOD_ID + ":mysterious_portal_frame_entity",
        FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            MysteriousPortalFrameBlockEntity(
                pos,
                state
            )
        }, RegisterBlock.MYSTERIOUS_PORTAL_FRAME).build(null))


    val ENFEEBLING_BOLT_ENTITY: EntityType<EnfeeblingBoltEntity> = Registry.register(
        Registry.ENTITY_TYPE,
        Identifier(AIO.MOD_ID, "enfeebling_bolt_entity"),
        FabricEntityTypeBuilder.create(
            SpawnGroup.MISC
        ) { entityType: EntityType<EnfeeblingBoltEntity>, world: World ->
            EnfeeblingBoltEntity(
                entityType,
                world
            )
        }.dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
    )
    
    val LAMBENT_TRIDENT_ENTITY: EntityType<LambentTridentEntity> = Registry.register(
        Registry.ENTITY_TYPE,
        Identifier(AIO.MOD_ID, "lambent_trident"),
        FabricEntityTypeBuilder.create(
            SpawnGroup.MISC
        ) { entityType: EntityType<LambentTridentEntity>, world: World ->
            LambentTridentEntity(
                entityType,
                world
            )
        }.dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build()
    )

    val VAMPIRIC_BOLT_ENTITY: EntityType<VampiricBoltEntity> = Registry.register(
        Registry.ENTITY_TYPE,
        Identifier(AIO.MOD_ID, "vampiric_bolt_entity"),
        FabricEntityTypeBuilder.create(
            SpawnGroup.MISC
        ) { entityType: EntityType<VampiricBoltEntity>, world: World ->
            VampiricBoltEntity(
                entityType,
                world
            )
        }.dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
    )

    fun registerAll(){

    }

    fun <T: BlockEntity> getBlockEntity(world: World, pos: BlockPos, entityType: BlockEntityType<T>): T?{
        val chk = world.getBlockEntity(pos, entityType)
        return if (chk.isPresent){
            chk.get()
        } else {
            null
        }
    }

}

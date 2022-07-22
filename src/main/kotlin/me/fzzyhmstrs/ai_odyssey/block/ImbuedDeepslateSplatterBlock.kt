package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.ConfiguratorInteractive
import me.fzzyhmstrs.ai_odyssey.registry.RegisterEnchantment
import me.fzzyhmstrs.ai_odyssey.screen.ImbuedSplatterScreenHandler
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.IndexedEnum
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.enumToIndexes
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityPose
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ImbuedDeepslateSplatterBlock(settings: Settings, fallbackHardness: Float): ImbuedBlock(settings, fallbackHardness), ConfiguratorInteractive {

    companion object{
        val SPLATTER = EnumProperty.of("splatter",Splatter::class.java)
        private val SPLATTER_VISIBLE = BooleanProperty.of("splatter_visible")
        val splatterMap: Map<Int,Splatter> by lazy { enumToIndexes(Splatter.values()) }

        enum class Splatter(private val str: String,val x: Int, val y: Int): StringIdentifiable, IndexedEnum{
            EYE_1("eye_1",0,0),
            EYE_2("eye_2",1,0),
            TALLY_1("tally_1",2,0),
            TALLY_2("tally_2",3,0),
            TALLY_3("tally_3",4,0),
            HAND_PRINT_1("hand_print_1",0,1),
            HAND_PRINT_2("hand_print_2",0,2),
            HAND_SMEAR_1("hand_smear_1",1,1),
            HAND_SMEAR_2("hand_smear_2",1,2),
            HAND_SMEAR_3("hand_smear_3",2,1),
            HAND_SMEAR_4("hand_smear_4",2,2),
            BLOOD_1("blood_1",0,3),
            BLOOD_2("blood_2",1,3),
            BLOOD_3("blood_3",2,3),
            BLOOD_4("blood_4",0,4),
            BLOOD_5("blood_5",1,4),
            PICTOGRAPH_1("pictograph_1",3,1),
            PICTOGRAPH_2("pictograph_2",4,1),
            PICTOGRAPH_3("pictograph_3",3,2),
            PICTOGRAPH_4("pictograph_4",4,2),
            ARROW_1("arrow_1",3,3),
            ARROW_2("arrow_2",4,3),
            ARROW_3("arrow_3",3,4),
            ARROW_4("arrow_4",4,4),
            X_1("x_1",2,4),
            NONE("none",-1,-1);

            override fun asString(): String {
                return this.str
            }

            override fun coordinatesToIndex(): Int{
                return this.x + 10 * this.y
            }
        }
    }




    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(SPLATTER,Splatter.NONE)?.with(SPLATTER_VISIBLE,false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(SPLATTER, SPLATTER_VISIBLE)
    }

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (player.isCreative && player.pose == EntityPose.CROUCHING){
            world.setBlockState(pos, state.cycle(SPLATTER))
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState
    ): ActionResult {
        return if (user != null) {
            user.openHandledScreen(state.createScreenHandlerFactory(world, pos))
            ActionResult.CONSUME
        } else {
            ActionResult.FAIL
        }
    }

    @Deprecated("Deprecated in Java")
    override fun createScreenHandlerFactory(
        state: BlockState,
        world: World,
        pos: BlockPos
    ): NamedScreenHandlerFactory {
        val text = this.name
        return SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, _: PlayerEntity ->
            ImbuedSplatterScreenHandler(
                syncId,
                inventory,
                ScreenHandlerContext.create(world, pos)
            )
        }, text)
    }
}

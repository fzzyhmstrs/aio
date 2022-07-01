package me.fzzyhmstrs.ai_odyssey.block

import me.fzzyhmstrs.ai_odyssey.configurator.ConfiguratorInteractive
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.IndexedEnum
import me.fzzyhmstrs.ai_odyssey.screen.MessageScreenHelper.enumToIndexes
import me.fzzyhmstrs.ai_odyssey.screen.PetroglyphRecipeScreenHandler
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class PetroglyphRecipeBlock(settings: Settings): Block(settings), ConfiguratorInteractive {

    companion object {
        private val FACING = HorizontalFacingBlock.FACING
        val INGREDIENT: EnumProperty<Ingredient> = EnumProperty.of("ingredient", Ingredient::class.java)
        val recipeMap: Map<Int, Ingredient> by lazy { enumToIndexes(Ingredient.values()) }


        enum class Ingredient(val x: Int, val y: Int): StringIdentifiable, IndexedEnum{

            DIAMOND(0,0),
            EMERALD(1,0),
            NONE(-1,-1);

            override fun asString(): String {
                return this.name
            }

            override fun coordinatesToIndex(): Int{
                return this.x + 10 * this.y
            }
        }

        /*private fun recipesToIndexes(): Map<Int,Ingredient>{
            val map: MutableMap<Int, Ingredient> = mutableMapOf()
            Ingredient.values().forEach {
                val index = it.coordinatesToIndex()
                map[index] = it
            }
            return map
        }*/
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING,INGREDIENT)
    }
    
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(INGREDIENT,Ingredient.NONE)?.with(FACING,ctx.playerFacing.opposite)
    }
    
    override fun interactWithConfigurator(
        world: World,
        user: PlayerEntity?,
        stack: ItemStack,
        pos: BlockPos,
        state: BlockState): ActionResult {
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
            PetroglyphRecipeScreenHandler(
                syncId,
                inventory,
                ScreenHandlerContext.create(world, pos)
            )
        }, text)
    }
}

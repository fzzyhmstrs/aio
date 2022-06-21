package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable

class PetroglyphRecipeBlock(settings: Settings): Block(settings), ConfiguratorInteractive {

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
        state: BlockState): ActionResult{
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

    companion object {
        private val FACING = HorizontalFacingBlock.FACING
        private val INGREDIENT: EnumProperty<Ingredient> = EnumProperty.of("ingredient", Ingredient::class.java)
        val recipeMap: Map<Int, Message> by lazy { recipesToIndexes() }


        enum class Ingredient(val x: Int, val y: Int): StringIdentifiable{

            DIAMOND(0,0),
            EMERALD(1,0),
            NONE(-1,-1);

            override fun asString(): String {
                return this.name
            }

            fun coordinatesToIndex(): Int{
                return this.x + 10 * this.y
            }
        }

        private fun recipesToIndexes(): Map<Int,Ingredient>{
            val map: MutableMap<Int, Ingredient> = mutableMapOf()
            Ingredient.values().forEach {
                val index = it.coordinatesToIndex()
                map[index] = it
            }
            return map
        }
    }

}

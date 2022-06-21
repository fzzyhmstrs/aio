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

        enum class Ingredient: StringIdentifiable{

            DIAMOND,
            EMERALD,
            NONE;

            override fun asString(): String {
                return this.name
            }
        }
    }

}

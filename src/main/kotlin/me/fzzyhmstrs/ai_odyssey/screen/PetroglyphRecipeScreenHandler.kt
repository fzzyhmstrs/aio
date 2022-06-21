package me.fzzyhmstrs.ai_odyssey.screen

import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext

class PetroglyphRecipeScreenHandler(
    syncID: Int,
    playerInventory: PlayerInventory,
    private val context: ScreenHandlerContext
): ScreenHandler(RegisterHandler.PETROGLYPH_RECIPE_SCREEN_HANDLER, syncID) {

    constructor(syncID: Int, playerInventory: PlayerInventory) : this(
        syncID,
        playerInventory,
        ScreenHandlerContext.EMPTY
    )

    override fun canUse(player: PlayerEntity?): Boolean {
        return canUse(this.context, player, RegisterBlock.PETROGLYPH_RECIPE)
    }

    override fun onButtonClick(player: PlayerEntity?, id: Int): Boolean {
        if (PetroglpyhRecipeBlock.recipeMap.containsKey(id)){
            val recipe = PetroglyphRecipeBlock.recipeMap[id]
            if (recipe != null) {
                context.run { world, pos ->
                    val state = world.getBlockState(pos)
                    world.setBlockState(pos,state.with(PetroglpyhRecipeBlock.INGREDIENT, recipe))
                    FacilityChimes.CONFIG_SUCCESS.playSound(world, pos)
                }
                return true
            }
        }
        return false
    }
}

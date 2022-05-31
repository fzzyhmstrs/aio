package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable

class PetroglyphRecipeBlock(settings: Settings): Block(settings) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(INGREDIENT)
    }

    companion object {
        private val INGREDIENT: EnumProperty<Ingredient> = EnumProperty.of("ingredient", Ingredient::class.java)

        enum class Ingredient: StringIdentifiable{

            DIAMOND,
            EMERALD;

            override fun asString(): String {
                return this.name
            }
        }
    }

}
package me.fzzyhmstrs.ai_odyssey.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos

class CrystallineSwitchBlock(settings: Settings): BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        TODO("Not yet implemented")
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LIT, SWITCH_COLOR)
    }

    companion object{

        private val LIT = Properties.LIT
        private val SWITCH_COLOR = EnumProperty.of("switch_color",SwitchColor::class.java)

        enum class SwitchColor: StringIdentifiable {

            BLUE,
            GREEN,
            RED,
            PINK,
            YELLOW;

            override fun asString(): String {
                return this.name
            }
        }

    }

}
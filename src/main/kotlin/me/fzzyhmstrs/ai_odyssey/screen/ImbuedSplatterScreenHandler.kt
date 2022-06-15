package me.fzzyhmstrs.ai_odyssey.screen

import me.fzzyhmstrs.ai_odyssey.block.ImbuedDeepslateSplatterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterBlock
import me.fzzyhmstrs.ai_odyssey.registry.RegisterHandler
import me.fzzyhmstrs.ai_odyssey.util.FacilityChimes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.sound.SoundEvents

class ImbuedSplatterScreenHandler(
    syncID: Int,
    playerInventory: PlayerInventory,
    private val context: ScreenHandlerContext
): ScreenHandler(RegisterHandler.IMBUED_SPLATTER_SCREEN_HANDLER, syncID) {

    constructor(syncID: Int, playerInventory: PlayerInventory) : this(
        syncID,
        playerInventory,
        ScreenHandlerContext.EMPTY
    )

    override fun canUse(player: PlayerEntity?): Boolean {
        return canUse(this.context, player, RegisterBlock.IMBUED_DEEPSLATE_SPLATTER)
    }

    override fun onButtonClick(player: PlayerEntity, id: Int): Boolean {
        if (ImbuedDeepslateSplatterBlock.splatterMap.containsKey(id)){
            val splatter = ImbuedDeepslateSplatterBlock.splatterMap[id]
            if (splatter != null) {
                context.run { world, pos ->
                    val state = world.getBlockState(pos)
                    world.setBlockState(pos,state.with(ImbuedDeepslateSplatterBlock.SPLATTER,splatter))
                    FacilityChimes.CONFIG_SUCCESS.playSound(world, pos)
                }
                return true
            }
        }
        return false
    }
}
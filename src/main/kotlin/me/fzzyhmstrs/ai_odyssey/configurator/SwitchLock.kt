package me.fzzyhmstrs.ai_odyssey.configurator

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface SwitchLock: ConfiguratorInteractive {

    fun isUnlocked(world: World, pos: BlockPos): Boolean

}
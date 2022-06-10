package me.fzzyhmstrs.ai_odyssey.util

import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

enum class FacilityChimes {

    CONFIG_SUCCESS {
        override fun playSound(world: World, pos: BlockPos) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, SoundCategory.BLOCKS,2.0f, 2.0f)
        }
    },
    HIGH_SUCCESS {
        override fun playSound(world: World, pos: BlockPos) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS,2.0f, 2.0f)
        }
    },
    SUCCESS {
        override fun playSound(world: World, pos: BlockPos) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS,2.0f, 1.0f)
        }
    },
    FAILURE {
        override fun playSound(world: World, pos: BlockPos) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS,2.0f, 1.0f)
        }
    },
    NUM_CLICK {
        override fun playSound(world: World, pos: BlockPos) {
            world.playSound(null, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS,1.0f, 1.2f)
        }
    };

    abstract fun playSound(world: World, pos: BlockPos)
}
package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.amethyst_core.item_util.AbstractAugmentBookItem
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier

class BookOfLegendItem(settings: Settings): AbstractAugmentBookItem(settings) {
    override val loreTier: LoreTier = LoreTier.EXTREME_TIER
}
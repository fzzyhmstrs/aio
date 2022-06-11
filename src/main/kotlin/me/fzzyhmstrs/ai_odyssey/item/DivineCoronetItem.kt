package me.fzzyhmstrs.ai_odyssey.item

import me.fzzyhmstrs.amethyst_imbuement.item.ImbuedJewelryItem

class DivineCoronetItem(settings: Settings, ttn: String): ImbuedJewelryItem(settings, ttn) {

    override fun shieldingAdder(): Int {
        return 8
    }

}
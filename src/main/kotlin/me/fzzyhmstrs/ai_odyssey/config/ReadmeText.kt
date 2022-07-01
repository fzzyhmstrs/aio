package me.fzzyhmstrs.ai_odyssey.config

import me.fzzyhmstrs.amethyst_core.coding_util.SyncedConfigHelper

object ReadmeText: SyncedConfigHelper.ReadMeWriter {
    override fun readmeText(): List<String> {
        return listOf(
            "README",
            "Amethyst Imbuement: Odyssey",
            "------------------",
            "",
            "",
            "Scepters Config:",
            "The scepters config json tweaks the properties of the scepters in-game. You may want to tweak it if you feel like scepters have too many uses at once, or conversely if you feel that they run out of mana too quickly",
            "",
            "> resplendentDurability: define durability for the Resplendent Scepter (Extreme tier).",
            "> bladesDurability: define durability for the Scepter of Blades.",
            "> bladesDamage: set the attack damage the scepter of blades deals (with left click)",
            "> bloodWitchDurability: define durability for the Spite of the Blood Witch legendary scepter",
            "",
            "",
            "Facility Config:",
            "Configuration for the portal facility.",
            "",
            "> showBlood: If disabled, bloody messages and splatters won't appear in the portal facility when the aberration is in pursuit.",
            "> enableBossSkip: When enabled, the facility teleporters will be active as soon as the structure is loaded, allowing players to bypass the boss fight. Useful for playtesting or if you simply want to explore the Unknown Depths in peace."
        )
    }
}
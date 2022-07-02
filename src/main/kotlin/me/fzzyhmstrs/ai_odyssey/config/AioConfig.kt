package me.fzzyhmstrs.ai_odyssey.config

import com.google.gson.GsonBuilder
import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.tool.BloodWitchToolMaterial
import me.fzzyhmstrs.ai_odyssey.tool.ScepterLvl4ToolMaterial
import me.fzzyhmstrs.ai_odyssey.tool.ScepterOfBladesToolMaterial
import me.fzzyhmstrs.amethyst_core.coding_util.SyncedConfigHelper
import me.fzzyhmstrs.amethyst_core.coding_util.SyncedConfigHelper.gson
import me.fzzyhmstrs.amethyst_core.coding_util.SyncedConfigHelper.readOrCreate
import me.fzzyhmstrs.amethyst_core.registry.SyncedConfigRegistry
import me.fzzyhmstrs.amethyst_imbuement.config.AiConfig
import net.minecraft.network.PacketByteBuf

object AioConfig: SyncedConfigHelper.SyncedConfig {

    var scepters: Scepters
    var facility: Facility

    init{
        scepters = readOrCreate("scepters_v0.json", base = AIO.MOD_ID) { Scepters() }
        facility = readOrCreate("facility_v0.json", base = AIO.MOD_ID) { Facility() }
    }

    override fun initConfig(){
        SyncedConfigRegistry.registerConfig(AIO.MOD_ID,this)
        ReadmeText.writeReadMe("README.txt", AIO.MOD_ID)
    }

    class Scepters {
        var resplendentDurability: Int = ScepterLvl4ToolMaterial.defaultDurability()
        var bladesDurability: Int = ScepterOfBladesToolMaterial.defaultDurability()
        var bladesDamage: Float = ScepterOfBladesToolMaterial.defaultAttackDamage()
        var bloodWitchDurability: Int = BloodWitchToolMaterial.defaultDurability()
    }

    class Facility{
        var showBlood: Boolean = true
        var enableBossSkip: Boolean = false
    }

    override fun readFromServer(buf: PacketByteBuf) {
        scepters = gson.fromJson(buf.readString(), Scepters::class.java)
        facility = gson.fromJson(buf.readString(), Facility::class.java)
    }

    override fun writeToClient(buf: PacketByteBuf) {
        val gson = GsonBuilder().create()
        buf.writeString(gson.toJson(scepters))
        buf.writeString(gson.toJson(facility))
    }

}
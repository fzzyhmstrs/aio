package me.fzzyhmstrs.ai_odyssey.registry

import me.fzzyhmstrs.ai_odyssey.AIO
import me.fzzyhmstrs.ai_odyssey.particle.DrippingBloodFactory
import me.fzzyhmstrs.ai_odyssey.particle.FallingBloodFactory
import me.fzzyhmstrs.ai_odyssey.particle.LandingBloodFactory
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RegisterParticle {

    val DRIPPING_BLOOD = FabricParticleTypes.simple()
    val FALLING_BLOOD = FabricParticleTypes.simple()
    val LANDING_BLOOD = FabricParticleTypes.simple()

    fun registerParticleTypes(){
        Registry.register(Registry.PARTICLE_TYPE, Identifier(AIO.MOD_ID,"dripping_blood"), DRIPPING_BLOOD)
        Registry.register(Registry.PARTICLE_TYPE, Identifier(AIO.MOD_ID,"falling_blood"), FALLING_BLOOD)
        Registry.register(Registry.PARTICLE_TYPE, Identifier(AIO.MOD_ID,"landing_blood"), LANDING_BLOOD)
    }

    fun registerParticleFactories(){
        ParticleFactoryRegistry.getInstance().register(DRIPPING_BLOOD) { spriteProvider ->
            DrippingBloodFactory(spriteProvider)
        }
        ParticleFactoryRegistry.getInstance().register(FALLING_BLOOD) { spriteProvider ->
            FallingBloodFactory(spriteProvider)
        }
        ParticleFactoryRegistry.getInstance().register(LANDING_BLOOD) { spriteProvider ->
            LandingBloodFactory(spriteProvider)
        }
    }
}
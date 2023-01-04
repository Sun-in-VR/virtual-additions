package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class VAParticleTypes {
    public static final DefaultParticleType ACID_SPLASH_EMITTER;
    public static final DefaultParticleType ACID_SPLASH;
    public static void init() {}

    static{
        ACID_SPLASH_EMITTER = register("acid_splash_emitter");
        ACID_SPLASH = register("acid_splash");
    }

    private static <T extends ParticleEffect> ParticleType<T> register(String name, boolean alwaysShow, ParticleEffect.Factory<T> factory) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.complex(alwaysShow, factory));
    }

    private static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(alwaysShow));
    }

    private static DefaultParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(false));
    }
}

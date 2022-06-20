package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAParticleTypes {
    public static final DefaultParticleType ICY_FOG = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registry.PARTICLE_TYPE, idOf("icy_fog"), ICY_FOG);
    }
}

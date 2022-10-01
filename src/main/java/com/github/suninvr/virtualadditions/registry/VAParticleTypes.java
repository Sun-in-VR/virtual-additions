package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAParticleTypes {
    public static final DefaultParticleType ICY_FOG = FabricParticleTypes.simple();
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_TETHER_RING = FabricParticleTypes.complex(IoliteRingParticleEffect.FACTORY_TETHER);
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_ANCHOR_RING = FabricParticleTypes.complex(IoliteRingParticleEffect.FACTORY_ANCHOR);

    public static void init() {
        Registry.register(Registry.PARTICLE_TYPE, idOf("icy_fog"), ICY_FOG);
        Registry.register(Registry.PARTICLE_TYPE, idOf("iolite_tether_ring"), IOLITE_TETHER_RING);
        Registry.register(Registry.PARTICLE_TYPE, idOf("iolite_anchor_ring"), IOLITE_ANCHOR_RING);
    }
}

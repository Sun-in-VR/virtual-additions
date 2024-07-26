package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

@SuppressWarnings({"SameParameterValue", "deprecation", "unused"})
public class VAParticleTypes {
    public static final SimpleParticleType ACID_SPLASH_EMITTER;
    public static final SimpleParticleType ACID_SPLASH;
    public static final SimpleParticleType GREENCAP_SPORE;
    public static final SimpleParticleType SCRAPE_STEEL;
    public static final SimpleParticleType INTERFERENCE;
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_TETHER_RING;
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_ANCHOR_RING;

    static{
        ACID_SPLASH_EMITTER = register("acid_splash_emitter");
        ACID_SPLASH = register("acid_splash");
        GREENCAP_SPORE = register("greencap_spore");
        SCRAPE_STEEL = register("scrape_steel", true);
        INTERFERENCE = register("interference");
        IOLITE_TETHER_RING = register("warp_tether_ring", false, type -> IoliteRingParticleEffect.TETHER_CODEC, type -> IoliteRingParticleEffect.TETHER_PACKET_CODEC);
        IOLITE_ANCHOR_RING = register("warp_anchor_ring", false, type -> IoliteRingParticleEffect.ANCHOR_CODEC, type -> IoliteRingParticleEffect.ANCHOR_PACKET_CODEC);
    }

    public static void init() {}

    private static <T extends ParticleEffect> ParticleType<T> register(String name, boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codecGetter, Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), new ParticleType<T>(alwaysShow) {
            @Override
            public MapCodec<T> getCodec() {
                return (MapCodec)codecGetter.apply(this) ;
            }

            @Override
            public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
                return packetCodecGetter.apply(this);
            }
        });
    }

    private static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(alwaysShow));
    }

    private static SimpleParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(false));
    }
}

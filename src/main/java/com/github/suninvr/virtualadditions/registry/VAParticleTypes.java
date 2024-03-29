package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

@SuppressWarnings({"SameParameterValue", "deprecation", "unused"})
public class VAParticleTypes {
    public static final DefaultParticleType ACID_SPLASH_EMITTER;
    public static final DefaultParticleType ACID_SPLASH;
    public static final DefaultParticleType GREENCAP_SPORE;
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_TETHER_RING;
    public static final ParticleType<IoliteRingParticleEffect> IOLITE_ANCHOR_RING;

    static{
        ACID_SPLASH_EMITTER = register("acid_splash_emitter");
        ACID_SPLASH = register("acid_splash");
        GREENCAP_SPORE = register("greencap_spore");
        IOLITE_TETHER_RING = register("warp_tether_ring", false, IoliteRingParticleEffect.FACTORY_TETHER, type -> IoliteRingParticleEffect.TETHER_CODEC, type -> IoliteRingParticleEffect.TETHER_PACKET_CODEC);
        IOLITE_ANCHOR_RING = register("warp_anchor_ring", false, IoliteRingParticleEffect.FACTORY_ANCHOR, type -> IoliteRingParticleEffect.ANCHOR_CODEC, type -> IoliteRingParticleEffect.ANCHOR_PACKET_CODEC);
    }

    public static void init() {}

    private static <T extends ParticleEffect> ParticleType<T> register(String name, boolean alwaysShow, ParticleEffect.Factory<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter, Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), new ParticleType<T>(alwaysShow, factory) {
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

    private static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(alwaysShow));
    }

    private static DefaultParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(false));
    }
}

package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.particle.ColorfulPowerParticleEffect;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class VAParticleTypes {
    public static final SimpleParticleType ACID_SPLASH_EMITTER;
    public static final SimpleParticleType ACID_SPLASH;
    public static final SimpleParticleType GREENCAP_SPORE;
    public static final SimpleParticleType SCRAPE_STEEL;
    public static final SimpleParticleType INTERFERENCE;
    public static final SimpleParticleType SPECTRAL_POWER;
    public static final SimpleParticleType SOULBLOOM_LEAVES;
    public static final SimpleParticleType SPECTRAL_FLAME;
    public static final SimpleParticleType SMALL_SPECTRAL_FLAME;
    public static final SimpleParticleType SPRING_LOTUS_POLLEN;
    public static final SimpleParticleType SOUL_FIREFLY;
    public static final SimpleParticleType STATIC_SCULK_CHARGE_POP;
    public static final ParticleType<ColorfulPowerParticleEffect> COLORFUL_POWER;

    static{
        ACID_SPLASH_EMITTER = register("acid_splash_emitter");
        ACID_SPLASH = register("acid_splash");
        GREENCAP_SPORE = register("greencap_spore");
        SCRAPE_STEEL = register("scrape_steel", true);
        INTERFERENCE = register("interference");
        SOULBLOOM_LEAVES = register("soulbloom_leaves");
        SPECTRAL_FLAME = register("spectral_flame");
        SMALL_SPECTRAL_FLAME = register("small_spectral_flame");
        SPECTRAL_POWER = register("spectral_power");
        SPRING_LOTUS_POLLEN = register("spring_lotus_pollen");
        SOUL_FIREFLY = register("soul_firefly");
        STATIC_SCULK_CHARGE_POP = register("static_sculk_charge_pop");
        COLORFUL_POWER = register("colorful_power", false, type -> ColorfulPowerParticleEffect.CODEC, type -> ColorfulPowerParticleEffect.PACKET_CODEC);
    }

    public static void init() {}

    private static <T extends ParticleOptions> ParticleType<T> register(String name, boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codecGetter, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecGetter) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, VirtualAdditions.idOf(name), new ParticleType<T>(alwaysShow) {
            @Override
            public MapCodec<T> codec() {
                return codecGetter.apply(this) ;
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return packetCodecGetter.apply(this);
            }
        });
    }

    private static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(alwaysShow));
    }

    private static SimpleParticleType register(String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, VirtualAdditions.idOf(name), FabricParticleTypes.simple(false));
    }
}

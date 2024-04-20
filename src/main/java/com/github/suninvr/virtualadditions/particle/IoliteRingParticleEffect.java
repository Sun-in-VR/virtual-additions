package com.github.suninvr.virtualadditions.particle;

import com.github.suninvr.virtualadditions.client.particle.IoliteRingParticle;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@SuppressWarnings("deprecation")
public record IoliteRingParticleEffect(boolean inverse, double velocity, ParticleType<IoliteRingParticleEffect> type) implements ParticleEffect {
    public static final MapCodec<IoliteRingParticleEffect> TETHER_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.BOOL.fieldOf("inverse").forGetter(particleEffect -> particleEffect.inverse)).apply(instance, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_TETHER_RING))
    );
    public static final PacketCodec<RegistryByteBuf, IoliteRingParticleEffect> TETHER_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, effect -> effect.inverse, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_TETHER_RING)
    );
    public static final MapCodec<IoliteRingParticleEffect> ANCHOR_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.BOOL.fieldOf("inverse").forGetter(particleEffect -> particleEffect.inverse)).apply(instance, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_ANCHOR_RING))
    );
    public static final PacketCodec<RegistryByteBuf, IoliteRingParticleEffect> ANCHOR_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, effect -> effect.inverse, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_ANCHOR_RING)
    );

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    public double velocity() {
        return this.velocity;
    }
}

package com.github.suninvr.virtualadditions.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.Locale;

@SuppressWarnings("deprecation")
public record IoliteRingParticleEffect(boolean inverse, double velocity, ParticleType<IoliteRingParticleEffect> type) implements ParticleEffect {
    public static final Codec<IoliteRingParticleEffect> TETHER_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.BOOL.fieldOf("inverse").forGetter(particleEffect -> particleEffect.inverse)).apply(instance, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_TETHER_RING))
    );
    public static final PacketCodec<RegistryByteBuf, IoliteRingParticleEffect> TETHER_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, effect -> effect.inverse, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_TETHER_RING)
    );
    public static final Codec<IoliteRingParticleEffect> ANCHOR_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.BOOL.fieldOf("inverse").forGetter(particleEffect -> particleEffect.inverse)).apply(instance, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_ANCHOR_RING))
    );
    public static final PacketCodec<RegistryByteBuf, IoliteRingParticleEffect> ANCHOR_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, effect -> effect.inverse, b -> new IoliteRingParticleEffect(b, 0, VAParticleTypes.IOLITE_ANCHOR_RING)
    );
    public static final Factory<IoliteRingParticleEffect> FACTORY_TETHER = new Factory<IoliteRingParticleEffect>() {
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> particleType, StringReader stringReader, RegistryWrapper.WrapperLookup registryLookup) throws CommandSyntaxException {
            stringReader.expect(' ');
            boolean bl = stringReader.readBoolean();
            stringReader.expect(' ');
            double velocity = stringReader.readDouble();
            return new IoliteRingParticleEffect(bl, velocity, VAParticleTypes.IOLITE_TETHER_RING);
        }

        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> type, PacketByteBuf buf) {
            return new IoliteRingParticleEffect(buf.readBoolean(), buf.readDouble(), VAParticleTypes.IOLITE_TETHER_RING);
        }
    };

    public static final Factory<IoliteRingParticleEffect> FACTORY_ANCHOR = new Factory<IoliteRingParticleEffect>() {

        @Override
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> type, StringReader reader, RegistryWrapper.WrapperLookup registryLookup) throws CommandSyntaxException {
            reader.expect(' ');
            boolean bl = reader.readBoolean();
            reader.expect(' ');
            double velocity = reader.readDouble();
            return new IoliteRingParticleEffect(bl, velocity, VAParticleTypes.IOLITE_ANCHOR_RING);
        }

        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> type, PacketByteBuf buf) {
            return new IoliteRingParticleEffect(buf.readBoolean(), buf.readDouble(), VAParticleTypes.IOLITE_ANCHOR_RING);
        }
    };

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public String asString(RegistryWrapper.WrapperLookup registryLookup) {
        return null;
    }

    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.inverse);
    }

    public String asString() {
        return String.format(Locale.ROOT, "%s %b", Registries.PARTICLE_TYPE.getId(this.getType()), this.inverse);
    }

    public double velocity() {
        return this.velocity;
    }
}

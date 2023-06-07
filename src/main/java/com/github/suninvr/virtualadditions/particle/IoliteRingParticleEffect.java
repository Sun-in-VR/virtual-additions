package com.github.suninvr.virtualadditions.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

import java.util.Locale;

@SuppressWarnings("deprecation")
public record IoliteRingParticleEffect(boolean inverse, double velocity, ParticleType<IoliteRingParticleEffect> type) implements ParticleEffect {
    public static final Factory<IoliteRingParticleEffect> FACTORY_TETHER = new Factory<>() {
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            boolean bl = stringReader.readBoolean();
            stringReader.expect(' ');
            double velocity = stringReader.readDouble();
            return new IoliteRingParticleEffect(bl, velocity, VAParticleTypes.IOLITE_TETHER_RING);
        }

        @Override
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> type, PacketByteBuf buf) {
            return new IoliteRingParticleEffect(buf.readBoolean(), buf.readDouble(), VAParticleTypes.IOLITE_TETHER_RING);
        }
    };

    public static final Factory<IoliteRingParticleEffect> FACTORY_ANCHOR = new Factory<>() {
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            boolean bl = stringReader.readBoolean();
            stringReader.expect(' ');
            double velocity = stringReader.readDouble();
            return new IoliteRingParticleEffect(bl, velocity, VAParticleTypes.IOLITE_ANCHOR_RING);
        }

        @Override
        public IoliteRingParticleEffect read(ParticleType<IoliteRingParticleEffect> type, PacketByteBuf buf) {
            return new IoliteRingParticleEffect(buf.readBoolean(), buf.readDouble(), VAParticleTypes.IOLITE_ANCHOR_RING);
        }
    };

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    @Override
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

package com.github.suninvr.virtualadditions.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class ColorfulPowerParticleEffect implements ParticleEffect {
    private final Vector3fc color;
    public static final MapCodec<ColorfulPowerParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codecs.VECTOR_3F.fieldOf("color").forGetter(particle -> particle.color)
                    )
                    .apply(instance, ColorfulPowerParticleEffect::new));

    public static final PacketCodec<RegistryByteBuf, ColorfulPowerParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VECTOR_3F, particle -> particle.color, ColorfulPowerParticleEffect::new
    );

    public ColorfulPowerParticleEffect(Vector3fc color) {
        this.color = color;
    }

    @Override
    public ParticleType<?> getType() {
        return VAParticleTypes.COLORFUL_POWER;
    }

    public Vector3fc getColor() {
        return this.color;
    }
}

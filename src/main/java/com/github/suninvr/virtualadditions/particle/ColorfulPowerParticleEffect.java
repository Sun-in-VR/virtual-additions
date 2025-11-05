package com.github.suninvr.virtualadditions.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3fc;

public class ColorfulPowerParticleEffect implements ParticleOptions {
    private final Vector3fc color;
    public static final MapCodec<ColorfulPowerParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(particle -> particle.color)
                    )
                    .apply(instance, ColorfulPowerParticleEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ColorfulPowerParticleEffect> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, particle -> particle.color, ColorfulPowerParticleEffect::new
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

package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public record ExplosiveContentComponent(Optional<Integer> explosionStrength, Optional<Integer> fuseLength) {
    public static final ExplosiveContentComponent DEFAULT = new ExplosiveContentComponent(Optional.of(2), Optional.of(200));
    public static final Codec<ExplosiveContentComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, ExplosiveContentComponent> PACKET_CODEC;

    public int getExplosionStrength(){
        return this.explosionStrength.orElse(2);
    }

    public int getFuseLength(){
        return this.fuseLength.orElse(200);
    }

    public static DataComponentType.Builder<ExplosiveContentComponent> setCodecs(DataComponentType.Builder<ExplosiveContentComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    Codecs.createStrictOptionalFieldCodec(Codecs.NONNEGATIVE_INT, "explosion_strength").forGetter(ExplosiveContentComponent::explosionStrength),
                    Codecs.createStrictOptionalFieldCodec(Codecs.NONNEGATIVE_INT, "fuse_length").forGetter(ExplosiveContentComponent::fuseLength)
            ).apply(instance, ExplosiveContentComponent::new);
        });
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER.collect(PacketCodecs::optional), ExplosiveContentComponent::explosionStrength,
                PacketCodecs.INTEGER.collect(PacketCodecs::optional), ExplosiveContentComponent::fuseLength,
                ExplosiveContentComponent::new
        );
    }
}

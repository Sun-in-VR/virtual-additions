package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public record WarpTetherLocationComponent(Optional<BlockPos> pos) {
    public static final WarpTetherLocationComponent DEFAULT = new WarpTetherLocationComponent(Optional.empty());
    public static final Codec<WarpTetherLocationComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, WarpTetherLocationComponent> PACKET_CODEC;

    public WarpTetherLocationComponent(BlockPos pos) {
        this(Optional.of(pos));
    }

    public static DataComponentType.Builder<WarpTetherLocationComponent> setCodecs(DataComponentType.Builder<WarpTetherLocationComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    BlockPos.CODEC.optionalFieldOf("pos").forGetter(WarpTetherLocationComponent::pos)
            ).apply(instance, WarpTetherLocationComponent::new);
        });
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(BlockPos.PACKET_CODEC), WarpTetherLocationComponent::pos, WarpTetherLocationComponent::new
        );
    }
}

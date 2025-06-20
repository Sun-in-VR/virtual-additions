package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public record PortalCoreLocationComponent(Optional<BlockPos> pos) {
    public static final PortalCoreLocationComponent DEFAULT = new PortalCoreLocationComponent(Optional.empty());
    public static final Codec<PortalCoreLocationComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, PortalCoreLocationComponent> PACKET_CODEC;

    public PortalCoreLocationComponent(BlockPos pos) {
        this(Optional.of(pos));
    }

    public static ComponentType.Builder<PortalCoreLocationComponent> setCodecs(ComponentType.Builder<PortalCoreLocationComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.optionalFieldOf("pos").forGetter(PortalCoreLocationComponent::pos)
        ).apply(instance, PortalCoreLocationComponent::new));
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(BlockPos.PACKET_CODEC), PortalCoreLocationComponent::pos, PortalCoreLocationComponent::new
        );
    }

    public static PortalCoreLocationComponent of(BlockPos pos) {
        return new PortalCoreLocationComponent(pos);
    }
}

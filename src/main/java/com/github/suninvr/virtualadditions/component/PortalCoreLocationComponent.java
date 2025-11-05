package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record PortalCoreLocationComponent(Optional<BlockPos> pos) {
    public static final PortalCoreLocationComponent DEFAULT = new PortalCoreLocationComponent(Optional.empty());
    public static final Codec<PortalCoreLocationComponent> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, PortalCoreLocationComponent> PACKET_CODEC;

    public PortalCoreLocationComponent(BlockPos pos) {
        this(Optional.of(pos));
    }

    public static DataComponentType.Builder<PortalCoreLocationComponent> setCodecs(DataComponentType.Builder<PortalCoreLocationComponent> builder) {
        return builder.persistent(CODEC).networkSynchronized(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.optionalFieldOf("pos").forGetter(PortalCoreLocationComponent::pos)
        ).apply(instance, PortalCoreLocationComponent::new));
        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(BlockPos.STREAM_CODEC), PortalCoreLocationComponent::pos, PortalCoreLocationComponent::new
        );
    }

    public static PortalCoreLocationComponent of(BlockPos pos) {
        return new PortalCoreLocationComponent(pos);
    }
}

package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record RemoteNotifierBroadcastPayload(String TEXT, ItemStack STACK) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, RemoteNotifierBroadcastPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, RemoteNotifierBroadcastPayload::TEXT,
            ItemStack.OPTIONAL_STREAM_CODEC, RemoteNotifierBroadcastPayload::STACK,
            RemoteNotifierBroadcastPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.REMOTE_NOTIFIER_S2C_ID;
    }
}

package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetRemoteNotifierMessagePayload(String TEXT) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SetRemoteNotifierMessagePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SetRemoteNotifierMessagePayload::TEXT,
            SetRemoteNotifierMessagePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return VAPackets.SET_REMOTE_NOTIFIER_MESSAGE_ID;
    }
}

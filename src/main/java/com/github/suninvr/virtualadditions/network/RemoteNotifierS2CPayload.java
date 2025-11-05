package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record RemoteNotifierS2CPayload(String TEXT, ItemStack STACK) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, RemoteNotifierS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, RemoteNotifierS2CPayload::TEXT,
            ItemStack.OPTIONAL_STREAM_CODEC, RemoteNotifierS2CPayload::STACK,
            RemoteNotifierS2CPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.REMOTE_NOTIFIER_S2C_ID;
    }
}

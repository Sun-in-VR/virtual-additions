package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record RemoteNotifierS2CPayload(String TEXT, ItemStack STACK) implements CustomPayload {
    public static final PacketCodec<RegistryByteBuf, RemoteNotifierS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, RemoteNotifierS2CPayload::TEXT,
            ItemStack.OPTIONAL_PACKET_CODEC, RemoteNotifierS2CPayload::STACK,
            RemoteNotifierS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return VAPackets.REMOTE_NOTIFIER_S2C_ID;
    }
}

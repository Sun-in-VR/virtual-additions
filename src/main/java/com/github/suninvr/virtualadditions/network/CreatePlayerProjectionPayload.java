package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public class CreatePlayerProjectionPayload implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, CreatePlayerProjectionPayload> CODEC = StreamCodec.ofMember(CreatePlayerProjectionPayload::write, CreatePlayerProjectionPayload::new);
    private final UUID entityId;
    private final boolean removed;

    public CreatePlayerProjectionPayload(FriendlyByteBuf packetByteBuf) {
        this.entityId = packetByteBuf.readUUID();
        this.removed = packetByteBuf.readBoolean();
    }

    public CreatePlayerProjectionPayload(UUID entityId) {
        this.entityId = entityId;
        this.removed = false;
    }

    public CreatePlayerProjectionPayload(UUID entityId, boolean removed) {
        this.entityId = entityId;
        this.removed = removed;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityId);
        buf.writeBoolean(this.removed);
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public boolean isRemoved() {
        return removed;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.PLAYER_PROJECTION_S2C_ID;
    }
}

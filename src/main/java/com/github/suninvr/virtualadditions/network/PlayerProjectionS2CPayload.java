package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;

public class PlayerProjectionS2CPayload implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, PlayerProjectionS2CPayload> CODEC = PacketCodec.of(PlayerProjectionS2CPayload::write, PlayerProjectionS2CPayload::new);
    private final UUID entityId;
    private final boolean removed;

    public PlayerProjectionS2CPayload(PacketByteBuf packetByteBuf) {
        this.entityId = packetByteBuf.readUuid();
        this.removed = packetByteBuf.readBoolean();
    }

    public PlayerProjectionS2CPayload(UUID entityId) {
        this.entityId = entityId;
        this.removed = false;
    }

    public PlayerProjectionS2CPayload(UUID entityId, boolean removed) {
        this.entityId = entityId;
        this.removed = removed;
    }

    private void write(PacketByteBuf buf) {
        buf.writeUuid(this.entityId);
        buf.writeBoolean(this.removed);
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public boolean isRemoved() {
        return removed;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return VAPackets.PLAYER_PROJECTION_S2C_ID;
    }
}

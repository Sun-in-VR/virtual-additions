package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class PlayerProjectionMovementC2SPayload implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, PlayerProjectionMovementC2SPayload> CODEC = PacketCodec.of(PlayerProjectionMovementC2SPayload::write, PlayerProjectionMovementC2SPayload::new);
    private final UUID entityId;
    private final Vec3d pos;
    private final float pitch;
    private final float yaw;

    public PlayerProjectionMovementC2SPayload(PacketByteBuf packetByteBuf) {
        this.entityId = packetByteBuf.readUuid();
        this.pos = packetByteBuf.readVec3d();
        this.pitch = packetByteBuf.readFloat();
        this.yaw = packetByteBuf.readFloat();
    }

    public PlayerProjectionMovementC2SPayload(PlayerProjectionEntity entity) {
        this.entityId = entity.getUuid();
        this.pos = entity.getPos();
        this.pitch = entity.getPitch();
        this.yaw = entity.getYaw();
    }

    private void write(PacketByteBuf buf) {
        buf.writeUuid(this.entityId);
        buf.writeVec3d(this.pos);
        buf.writeFloat(this.pitch);
        buf.writeFloat(this.yaw);
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public Vec3d getPos() {
        return pos;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return VAPackets.PLAYER_PROJECTION_MOVEMENT_C2S_ID;
    }
}

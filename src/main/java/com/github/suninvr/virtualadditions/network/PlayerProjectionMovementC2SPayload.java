package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;
import java.util.UUID;

public record PlayerProjectionMovementC2SPayload(UUID entityId, Optional<Vec3d> pos, Optional<Float> pitch, Optional<Float> yaw) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, PlayerProjectionMovementC2SPayload> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC, PlayerProjectionMovementC2SPayload::entityId,
            PacketCodecs.optional(Vec3d.PACKET_CODEC), PlayerProjectionMovementC2SPayload::pos,
            PacketCodecs.FLOAT.collect(PacketCodecs::optional), PlayerProjectionMovementC2SPayload::pitch,
            PacketCodecs.FLOAT.collect(PacketCodecs::optional), PlayerProjectionMovementC2SPayload::yaw,
            PlayerProjectionMovementC2SPayload::new
    );

    public static PlayerProjectionMovementC2SPayload createFull(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUuid(), Optional.of(entity.getPos()), Optional.of(entity.getPitch()), Optional.of(entity.getYaw()));
    }

    public static PlayerProjectionMovementC2SPayload createPosOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUuid(), Optional.of(entity.getPos()), Optional.empty(), Optional.empty());
    }

    public static PlayerProjectionMovementC2SPayload createAnglesOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUuid(), Optional.empty(), Optional.of(entity.getPitch()), Optional.of(entity.getYaw()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return VAPackets.PLAYER_PROJECTION_MOVEMENT_C2S_ID;
    }
}

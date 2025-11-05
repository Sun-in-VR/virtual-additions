package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public record PlayerProjectionMovementC2SPayload(UUID entityId, Optional<Vec3> pos, Optional<Float> pitch, Optional<Float> yaw) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, PlayerProjectionMovementC2SPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, PlayerProjectionMovementC2SPayload::entityId,
            ByteBufCodecs.optional(Vec3.STREAM_CODEC), PlayerProjectionMovementC2SPayload::pos,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional), PlayerProjectionMovementC2SPayload::pitch,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional), PlayerProjectionMovementC2SPayload::yaw,
            PlayerProjectionMovementC2SPayload::new
    );

    public static PlayerProjectionMovementC2SPayload createFull(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUUID(), Optional.of(entity.position()), Optional.of(entity.getXRot()), Optional.of(entity.getYRot()));
    }

    public static PlayerProjectionMovementC2SPayload createPosOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUUID(), Optional.of(entity.position()), Optional.empty(), Optional.empty());
    }

    public static PlayerProjectionMovementC2SPayload createAnglesOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementC2SPayload(entity.getUUID(), Optional.empty(), Optional.of(entity.getXRot()), Optional.of(entity.getYRot()));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.PLAYER_PROJECTION_MOVEMENT_C2S_ID;
    }
}

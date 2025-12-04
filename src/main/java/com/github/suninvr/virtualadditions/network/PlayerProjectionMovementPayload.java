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

public record PlayerProjectionMovementPayload(UUID entityId, Optional<Vec3> pos, Optional<Float> pitch, Optional<Float> yaw) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, PlayerProjectionMovementPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, PlayerProjectionMovementPayload::entityId,
            ByteBufCodecs.optional(Vec3.STREAM_CODEC), PlayerProjectionMovementPayload::pos,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional), PlayerProjectionMovementPayload::pitch,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional), PlayerProjectionMovementPayload::yaw,
            PlayerProjectionMovementPayload::new
    );

    public static PlayerProjectionMovementPayload createFull(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementPayload(entity.getUUID(), Optional.of(entity.position()), Optional.of(entity.getXRot()), Optional.of(entity.getYRot()));
    }

    public static PlayerProjectionMovementPayload createPosOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementPayload(entity.getUUID(), Optional.of(entity.position()), Optional.empty(), Optional.empty());
    }

    public static PlayerProjectionMovementPayload createAnglesOnly(PlayerProjectionEntity entity) {
        return new PlayerProjectionMovementPayload(entity.getUUID(), Optional.empty(), Optional.of(entity.getXRot()), Optional.of(entity.getYRot()));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.PLAYER_PROJECTION_MOVEMENT_C2S_ID;
    }
}

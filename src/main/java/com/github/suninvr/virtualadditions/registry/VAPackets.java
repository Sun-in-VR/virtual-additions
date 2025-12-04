package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.network.*;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveMenu;
import com.github.suninvr.virtualadditions.screen.RemoteNotifierMenu;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAPackets {

    public static CustomPacketPayload.Type<SetEntangledSlotPayload> ENTANGLEMENT_DRIVE_C2S_ID = new CustomPacketPayload.Type<>(idOf("entanglement_drive_c2s"));
    public static CustomPacketPayload.Type<RemoteNotifierBroadcastPayload> REMOTE_NOTIFIER_S2C_ID = new CustomPacketPayload.Type<>(idOf("remote_notifier_s2c"));
    public static CustomPacketPayload.Type<SetRemoteNotifierMessagePayload> SET_REMOTE_NOTIFIER_MESSAGE_ID = new CustomPacketPayload.Type<>(idOf("set_remote_notifier_message"));
    public static CustomPacketPayload.Type<ColoringRecipesPayload> COLORING_STATION_S2C_ID = new CustomPacketPayload.Type<>(idOf("coloring_station_s2c"));
    public static CustomPacketPayload.Type<CreatePlayerProjectionPayload> PLAYER_PROJECTION_S2C_ID = new CustomPacketPayload.Type<>(idOf("player_projection_s2c"));
    public static CustomPacketPayload.Type<PlayerProjectionMovementPayload> PLAYER_PROJECTION_MOVEMENT_C2S_ID = new CustomPacketPayload.Type<>(idOf("player_projection_movement_c2s"));

    static {
        PayloadTypeRegistry.playC2S().register(ENTANGLEMENT_DRIVE_C2S_ID, SetEntangledSlotPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ENTANGLEMENT_DRIVE_C2S_ID, (payload, context) -> {
            if (context.player().containerMenu instanceof EntanglementDriveMenu screenHandler) {
                screenHandler.setActiveSlotIndex(payload.getSlotIndex());
                screenHandler.setActivePlayerId(payload.getPlayerId());
                screenHandler.decrementPaymentSlot();
                screenHandler.getEntity().ifPresent( blockEntity -> blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), VASoundEvents.BLOCK_ENTANGLEMENT_DRIVE_USE, SoundSource.BLOCKS, 1.0F, 1.0F));
                VAAdvancementCriteria.USE_ENTANGLEMENT_DRIVE.trigger(context.player());
            }
        });

        PayloadTypeRegistry.playC2S().register(PLAYER_PROJECTION_MOVEMENT_C2S_ID, PlayerProjectionMovementPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_PROJECTION_MOVEMENT_C2S_ID, (payload, context) -> {
            if (context.player().level() != null && context.player().level().getEntity(payload.entityId()) instanceof PlayerProjectionEntity entity) {
                payload.pos().ifPresent(pos -> {
                    double dx = pos.x - entity.xo;
                    double dy = pos.y - entity.yo;
                    double dz = pos.z - entity.zo;
                    entity.move(MoverType.PLAYER, new Vec3(dx, dy, dz));
                });
                if (payload.pitch().isPresent() && payload.yaw().isPresent()) {
                    entity.absSnapRotationTo(payload.yaw().get(), payload.pitch().get());
                    entity.yRotO = entity.yBodyRot = entity.yHeadRot = entity.getYRot();
                }
            }
        });

        PayloadTypeRegistry.playC2S().register(SET_REMOTE_NOTIFIER_MESSAGE_ID, SetRemoteNotifierMessagePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SET_REMOTE_NOTIFIER_MESSAGE_ID, ((payload, context) -> {
            if (context.player().containerMenu instanceof RemoteNotifierMenu menu) {
                menu.setText(payload.TEXT());
            }
        }));

        PayloadTypeRegistry.playS2C().register(REMOTE_NOTIFIER_S2C_ID, RemoteNotifierBroadcastPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(COLORING_STATION_S2C_ID, ColoringRecipesPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PLAYER_PROJECTION_S2C_ID, CreatePlayerProjectionPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SET_REMOTE_NOTIFIER_MESSAGE_ID, SetRemoteNotifierMessagePayload.CODEC);

    }

    public static void init(){}


}

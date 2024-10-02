package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.network.EntanglementDriveC2SPayload;
import com.github.suninvr.virtualadditions.network.RemoteNotifierS2CPayload;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAPackets {

    public static Identifier WIND_UPDATE_VELOCITY = idOf("wind_update_velocity");
    public static CustomPayload.Id<EntanglementDriveC2SPayload> ENTANGLEMENT_DRIVE_C2S_ID = new CustomPayload.Id<>(idOf("entanglement_drive_c2s"));
    public static CustomPayload.Id<RemoteNotifierS2CPayload> REMOTE_NOTIFIER_S2C_ID = new CustomPayload.Id<>(idOf("remote_notifier_s2c"));

    static {
        PayloadTypeRegistry.playC2S().register(ENTANGLEMENT_DRIVE_C2S_ID, EntanglementDriveC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ENTANGLEMENT_DRIVE_C2S_ID, (payload, context) -> {
            if (context.player().currentScreenHandler instanceof EntanglementDriveScreenHandler screenHandler) {
                screenHandler.setActiveSlotIndex(payload.getSlotIndex());
                screenHandler.setActivePlayerId(payload.getPlayerId());
                screenHandler.decrementPaymentSlot();
                screenHandler.getEntity().ifPresent( blockEntity -> blockEntity.getWorld().playSound(null, blockEntity.getPos(), VASoundEvents.BLOCK_ENTANGLEMENT_DRIVE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F));
                VAAdvancementCriteria.USE_ENTANGLEMENT_DRIVE.trigger(context.player());
            }
        });

        PayloadTypeRegistry.playS2C().register(REMOTE_NOTIFIER_S2C_ID, RemoteNotifierS2CPayload.CODEC);
    }

    public static void init(){}


}

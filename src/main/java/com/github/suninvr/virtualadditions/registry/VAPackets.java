package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class VAPackets {

    public static Identifier ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_active_slot_sync");
    public static Identifier ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_selected_slot_sync");
    public static Identifier ENTANGLEMENT_DRIVE_SET_ACTIVE_SLOT_ID = VirtualAdditions.idOf("entanglement_drive_set_active_slot");

    static {
        ServerPlayNetworking.registerGlobalReceiver(ENTANGLEMENT_DRIVE_SET_ACTIVE_SLOT_ID, ((server, player, handler, buf, responseSender) -> {
            if (player.currentScreenHandler instanceof EntanglementDriveScreenHandler screenHandler) {
                screenHandler.setPlayerSlot(player);
            }
        }));
    }

    public static void init(){}


}

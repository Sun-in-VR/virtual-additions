package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAScreenHandler {

    public static final ScreenHandlerType<EntanglementDriveScreenHandler> ENTANGLEMENT_DRIVE_SCREEN_HANDLER;

    static {
        ENTANGLEMENT_DRIVE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(idOf("entanglement_drive"), EntanglementDriveScreenHandler::new);
    }

    public static void init(){};
}

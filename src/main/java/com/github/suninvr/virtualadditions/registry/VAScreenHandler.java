package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("deprecation")
public class VAScreenHandler {

    public static final ScreenHandlerType<EntanglementDriveScreenHandler> ENTANGLEMENT_DRIVE;
    public static final ScreenHandlerType<ColoringStationScreenHandler> COLORING_STATION;

    static {
        ENTANGLEMENT_DRIVE = ScreenHandlerRegistry.registerExtended(idOf("entanglement_drive"), EntanglementDriveScreenHandler::new);
        COLORING_STATION = ScreenHandlerRegistry.registerExtended(idOf("coloring_station"), ColoringStationScreenHandler::new);
    }

    public static void init(){}
}

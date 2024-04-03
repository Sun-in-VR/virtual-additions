package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("deprecation")
public class VAScreenHandler {

    public static final ScreenHandlerType<EntanglementDriveScreenHandler> ENTANGLEMENT_DRIVE;
    public static final ScreenHandlerType<ColoringStationScreenHandler> COLORING_STATION;

    static {

        ENTANGLEMENT_DRIVE = register(idOf("entanglement_drive"), EntanglementDriveScreenHandler::new);
        COLORING_STATION = register(idOf("coloring_station"), ColoringStationScreenHandler::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void init(){}
}

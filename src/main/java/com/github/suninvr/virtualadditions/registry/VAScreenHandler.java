package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("deprecation")
public class VAScreenHandler {

    public static final MenuType<EntanglementDriveScreenHandler> ENTANGLEMENT_DRIVE;
    public static final MenuType<ColoringStationScreenHandler> COLORING_STATION;

    static {

        ENTANGLEMENT_DRIVE = register(idOf("entanglement_drive"), EntanglementDriveScreenHandler::new);
        COLORING_STATION = register(idOf("coloring_station"), ColoringStationScreenHandler::new);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static void init(){}
}

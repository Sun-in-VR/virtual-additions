package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.screen.ColoringStationMenu;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveMenu;
import com.github.suninvr.virtualadditions.screen.RemoteNotifierMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("deprecation")
public class VAMenus {

    public static final MenuType<EntanglementDriveMenu> ENTANGLEMENT_DRIVE;
    public static final MenuType<ColoringStationMenu> COLORING_STATION;
    public static final MenuType<RemoteNotifierMenu> REMOTE_NOTIFIER;

    static {

        ENTANGLEMENT_DRIVE = register(idOf("entanglement_drive"), EntanglementDriveMenu::new);
        COLORING_STATION = register(idOf("coloring_station"), ColoringStationMenu::new);
        REMOTE_NOTIFIER = register(idOf("remote_notifier"), RemoteNotifierMenu::new);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(Identifier id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static void init(){}
}

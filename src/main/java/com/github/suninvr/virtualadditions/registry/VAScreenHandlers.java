package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import com.github.suninvr.virtualadditions.screenhandler.WorkbenchScreenHandler;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAScreenHandlers {
    public static void init(){}
    public static ScreenHandlerType<WorkbenchScreenHandler> WORKBENCH_SCREEN_HANDLER;
    static {
        WORKBENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(idOf("workbench"), WorkbenchScreenHandler::new);
    }
}

package com.github.suninvr.virtualadditions.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class GildedToolUtil {
    public static FabricItemSettings settingsOf(Item item) {
        FabricItemSettings settings = new FabricItemSettings();
        if (item.isFireproof()) settings = settings.fireproof();
        return settings;
    }
}

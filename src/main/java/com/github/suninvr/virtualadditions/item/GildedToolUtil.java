package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.enums.GildedToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

public class GildedToolUtil {
    public static FabricItemSettings settingsOf(Item item) {
        FabricItemSettings settings = new FabricItemSettings();
        if (item.isFireproof()) settings = settings.fireproof();
        return settings;
    }
}

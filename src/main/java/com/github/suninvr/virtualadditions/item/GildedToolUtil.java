package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GildedToolUtil {
    public static FabricItemSettings settingsOf(Item item) {
        FabricItemSettings settings = new FabricItemSettings();
        if (item.isFireproof()) settings = settings.fireproof();
        return settings;
    }

    public static GildType getGildType(ItemStack itemStack) {
        return getGildType(itemStack.getItem());
    }

    public static GildType getGildType(Item item) {
        if (item instanceof GildedToolItem gildedToolItem) return gildedToolItem.getGildType();
        return GildType.NONE;
    }
}

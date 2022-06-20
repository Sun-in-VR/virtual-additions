package com.github.suninvr.virtualadditions.item.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import com.github.suninvr.virtualadditions.item.enums.GildType;

public interface GildedToolItem{

    ToolMaterial getBaseMaterial();
    GildType getGildType();

    static GildType getGildType(ItemStack stack) {
        if (stack.getItem()instanceof GildedToolItem gildedToolItem) {
            return gildedToolItem.getGildType();
        }
        return GildType.NONE;
    }


}

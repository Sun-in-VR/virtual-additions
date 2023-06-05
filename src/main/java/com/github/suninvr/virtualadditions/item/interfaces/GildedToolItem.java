package com.github.suninvr.virtualadditions.item.interfaces;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public interface GildedToolItem{

    ToolMaterial getBaseMaterial();

    GildType getGildType();

    static GildType getGildType(ItemStack stack) {
        if (stack.getItem()instanceof GildedToolItem gildedToolItem) {
            return gildedToolItem.getGildType();
        }
        return GildTypes.NONE;
    }


}

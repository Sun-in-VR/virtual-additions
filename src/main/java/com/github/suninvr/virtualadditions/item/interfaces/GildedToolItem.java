package com.github.suninvr.virtualadditions.item.interfaces;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

@SuppressWarnings("unused")
public interface GildedToolItem extends ItemConvertible {

    ToolMaterial getBaseMaterial();

    GildType getGildType();

    Item getBaseItem();

    static GildType getGildType(ItemStack stack) {
        if (stack.getItem()instanceof GildedToolItem gildedToolItem) {
            return gildedToolItem.getGildType();
        }
        return GildTypes.NONE;
    }


}

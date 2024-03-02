package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class GildedToolUtil {
    public static Item.Settings settingsOf(Item item) {
        Item.Settings settings = new Item.Settings();
        if (item.isFireproof()) settings = settings.fireproof();
        return settings;
    }

    public static GildType getGildType(ItemStack itemStack) {
        return getGildType(itemStack.getItem());
    }

    public static GildType getGildType(Item item) {
        if (item instanceof GildedToolItem gildedToolItem) return gildedToolItem.getGildType();
        return GildTypes.NONE;
    }

    public static ItemStack getBaseStack(ItemStack stack) {
        ItemStack baseStack = stack.copy();
        if (stack.getItem() instanceof GildedToolItem gildedToolItem) {
            baseStack = gildedToolItem.getBaseItem().getDefaultStack();
            baseStack.copyComponentsFrom(stack.getComponents());
        }
        return baseStack;
    }
}

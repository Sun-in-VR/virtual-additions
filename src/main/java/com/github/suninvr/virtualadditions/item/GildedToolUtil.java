package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GildedToolUtil {

    public static Item.Settings settingsOf(Item.Settings settings, Item baseItem, GildType type) {
        settings.translationKey(baseItem.getTranslationKey());
        if (baseItem.getComponents().contains(DataComponentTypes.DAMAGE_RESISTANT)) settings.fireproof();
        return settings.attributeModifiers(type.createAttributeModifiers(baseItem));
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
            baseStack.applyComponentsFrom(stack.getComponents());
        }
        return baseStack;
    }
}

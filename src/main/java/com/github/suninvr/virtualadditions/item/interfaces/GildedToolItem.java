package com.github.suninvr.virtualadditions.item.interfaces;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public interface GildedToolItem extends ItemConvertible {

    GildType getGildType();

    Item getBaseItem();

    static GildType getGildType(ItemStack stack) {
        if (stack.getItem()instanceof GildedToolItem gildedToolItem) {
            return gildedToolItem.getGildType();
        }
        return GildTypes.NONE;
    }

    //static float getAttackDamageFromBaseItem(Item item, ToolMaterial material) {
    //    AttributeModifiersComponent component;
    //    double[] d = {0};
    //    if ((component = item.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS)) != null) {
    //        component.modifiers().forEach(entry -> {
    //            if (entry.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
    //                d[0] = entry.modifier().value() - material.attackDamageBonus();
    //            }
    //        });
    //    }
    //    return (float) d[0];
    //}

    //static float getAttackSpeedFromBaseItem(Item item, ToolMaterial material) {
    //    AttributeModifiersComponent component;
    //    double[] d = {0};
    //    if ((component = item.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS)) != null) {
    //        component.modifiers().forEach(entry -> {
    //            if (entry.attribute().equals(EntityAttributes.ATTACK_SPEED)) {
    //                d[0] = entry.modifier().value() - material.attackDamageBonus();
    //            }
    //        });
    //    }
    //    return (float) d[0];
    //}


}

package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class GildedToolUtil {

    public static Item.Settings settingsOf(Item.Settings settings, Item baseItem, GildType type) {
        settings.translationKey(baseItem.getTranslationKey());
        if (baseItem.getComponents().contains(DataComponentTypes.DAMAGE_RESISTANT)) settings.fireproof();
        return settings.attributeModifiers(type.createAttributeModifiers(baseItem));
    }

    public static Item.Settings halberdSettings(Item.Settings settings, ToolMaterial material, float attackDamage, float attackSpeed) {
        return material.applySwordSettings(settings, attackDamage, attackSpeed).attributeModifiers(halberdAttributes(attackDamage, attackSpeed));
    }

    public static AttributeModifiersComponent halberdAttributes(float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(idOf("halberd_reach"), 1, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
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

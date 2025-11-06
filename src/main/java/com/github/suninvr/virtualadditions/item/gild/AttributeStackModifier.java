package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class AttributeStackModifier extends StackModifier<ItemAttributeModifiers>{
    private final List<ItemAttributeModifiers.Entry> attributeModifiers;
    
    public AttributeStackModifier(ItemAttributeModifiers.Entry... attributeModifiers) {
        super(DataComponents.ATTRIBUTE_MODIFIERS);
        this.attributeModifiers = List.of(attributeModifiers);
    }
    
    public AttributeStackModifier(TagKey<Item> appliesTo, ItemAttributeModifiers.Entry... attributeModifiers) {
        super(DataComponents.ATTRIBUTE_MODIFIERS, appliesTo);
        this.attributeModifiers = List.of(attributeModifiers);
    }
    
    public AttributeStackModifier(TagKey<Item> appliesTo, Holder<Attribute> attribute, EquipmentSlotGroup slot, AttributeModifier modifier) {
        super(DataComponents.ATTRIBUTE_MODIFIERS, appliesTo);
        this.attributeModifiers = List.of(
                new ItemAttributeModifiers.Entry(attribute, modifier, slot, 
                        new ItemAttributeModifiers.Display.OverrideText(CommonComponents.space().append(
                                Component.translatable("attribute.modifier.equals." + modifier.operation().id(),
                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(modifier.amount()),
                                Component.translatable(attribute.value().getDescriptionId())).withStyle(ChatFormatting.DARK_GREEN)))));
    }
    
    @SafeVarargs
    public AttributeStackModifier(Holder<Attribute> attribute, float f, AttributeModifier.Operation operation, TagKey<Item>... appliesTo) {
        super(DataComponents.ATTRIBUTE_MODIFIERS, stack -> {
            for (TagKey<Item> tag : appliesTo) if (stack.is(tag)) return true;
            return false;
        });
        AttributeModifier modifier = new AttributeModifier(idOf(attribute.unwrapKey().map(key -> key.identifier().getPath()).orElse("") + "_from_gilded_tool"), f, operation);
        this.attributeModifiers = List.of(
                new ItemAttributeModifiers.Entry(attribute, modifier, EquipmentSlotGroup.MAINHAND, 
                        new ItemAttributeModifiers.Display.OverrideText(CommonComponents.space().append(
                                Component.translatable("attribute.modifier.equals." + modifier.operation().id(),
                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(modifier.amount()),
                                Component.translatable(attribute.value().getDescriptionId())).withStyle(ChatFormatting.DARK_GREEN)))));
    }

    public AttributeStackModifier(Holder<Attribute> attribute, float f, AttributeModifier.Operation operation) {
        super(DataComponents.ATTRIBUTE_MODIFIERS, ALWAYS_TRUE);
        AttributeModifier modifier = new AttributeModifier(idOf(attribute.unwrapKey().map(key -> key.identifier().getPath()).orElse("") + "_from_gilded_tool"), f, operation);
        this.attributeModifiers = List.of(
                new ItemAttributeModifiers.Entry(attribute, modifier, EquipmentSlotGroup.MAINHAND,
                        new ItemAttributeModifiers.Display.OverrideText(CommonComponents.space().append(
                                Component.translatable("attribute.modifier.equals." + modifier.operation().id(),
                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(modifier.amount()),
                                Component.translatable(attribute.value().getDescriptionId())).withStyle(ChatFormatting.DARK_GREEN)))));
    }

    @Override
    protected ItemAttributeModifiers modifyComponent(ItemAttributeModifiers component) {
        ArrayList<ItemAttributeModifiers.Entry> gildModifiers = new ArrayList<>(this.attributeModifiers);
        ArrayList<ItemAttributeModifiers.Entry> baseModifiers = new ArrayList<>(component.modifiers());
        ArrayList<ItemAttributeModifiers.Entry> appliedModifiers = new ArrayList<>(baseModifiers);
        for (ItemAttributeModifiers.Entry modifier : gildModifiers) {
            boolean modifiedExisting = false;
            for (ItemAttributeModifiers.Entry modifier1 : baseModifiers) {
                if (modifier1.attribute().equals(modifier.attribute()) && modifier1.slot().equals(modifier.slot())) {
                    modifiedExisting = true;
                    double d = modifier1.modifier().amount();
                    double e = modifier.modifier().amount();
                    d += switch (modifier.modifier().operation()) {
                        case ADD_VALUE -> e;
                        case ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL -> e * d;
                    };
                    AttributeModifier combinedModifier = new AttributeModifier(modifier1.modifier().id(), d, modifier1.modifier().operation());
                    appliedModifiers.remove(modifier1);
                    appliedModifiers.add(new ItemAttributeModifiers.Entry(modifier1.attribute(), combinedModifier, modifier1.slot(), modifier1.display()));
                }
            }
            if (!modifiedExisting) appliedModifiers.add(modifier);
        }
        return new ItemAttributeModifiers(appliedModifiers);
    }
}

package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class AttributeStackModifier extends StackModifier<AttributeModifiersComponent>{
    private final List<AttributeModifiersComponent.Entry> attributeModifiers;
    
    public AttributeStackModifier(AttributeModifiersComponent.Entry... attributeModifiers) {
        super(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        this.attributeModifiers = List.of(attributeModifiers);
    }
    
    public AttributeStackModifier(TagKey<Item> appliesTo, AttributeModifiersComponent.Entry... attributeModifiers) {
        super(DataComponentTypes.ATTRIBUTE_MODIFIERS, appliesTo);
        this.attributeModifiers = List.of(attributeModifiers);
    }
    
    public AttributeStackModifier(TagKey<Item> appliesTo, RegistryEntry<EntityAttribute> attribute, AttributeModifierSlot slot, EntityAttributeModifier modifier) {
        super(DataComponentTypes.ATTRIBUTE_MODIFIERS, appliesTo);
        this.attributeModifiers = List.of(
                new AttributeModifiersComponent.Entry(attribute, modifier, slot, 
                        new AttributeModifiersComponent.Display.Override(ScreenTexts.space().append(
                                Text.translatable("attribute.modifier.equals." + modifier.operation().getId(),
                                AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                                Text.translatable(attribute.value().getTranslationKey())).formatted(Formatting.DARK_GREEN)))));
    }
    
    @SafeVarargs
    public AttributeStackModifier(RegistryEntry<EntityAttribute> attribute, float f, EntityAttributeModifier.Operation operation, TagKey<Item>... appliesTo) {
        super(DataComponentTypes.ATTRIBUTE_MODIFIERS, stack -> {
            for (TagKey<Item> tag : appliesTo) if (stack.isIn(tag)) return true;
            return false;
        });
        EntityAttributeModifier modifier = new EntityAttributeModifier(idOf(attribute.getKey().map(key -> key.getValue().getPath()).orElse("") + "_from_gilded_tool"), f, operation);
        this.attributeModifiers = List.of(
                new AttributeModifiersComponent.Entry(attribute, modifier, AttributeModifierSlot.MAINHAND, 
                        new AttributeModifiersComponent.Display.Override(ScreenTexts.space().append(
                                Text.translatable("attribute.modifier.equals." + modifier.operation().getId(),
                                AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                                Text.translatable(attribute.value().getTranslationKey())).formatted(Formatting.DARK_GREEN)))));
    }

    public AttributeStackModifier(RegistryEntry<EntityAttribute> attribute, float f, EntityAttributeModifier.Operation operation) {
        super(DataComponentTypes.ATTRIBUTE_MODIFIERS, ALWAYS_TRUE);
        EntityAttributeModifier modifier = new EntityAttributeModifier(idOf(attribute.getKey().map(key -> key.getValue().getPath()).orElse("") + "_from_gilded_tool"), f, operation);
        this.attributeModifiers = List.of(
                new AttributeModifiersComponent.Entry(attribute, modifier, AttributeModifierSlot.MAINHAND,
                        new AttributeModifiersComponent.Display.Override(ScreenTexts.space().append(
                                Text.translatable("attribute.modifier.equals." + modifier.operation().getId(),
                                AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                                Text.translatable(attribute.value().getTranslationKey())).formatted(Formatting.DARK_GREEN)))));
    }

    @Override
    protected AttributeModifiersComponent modifyComponent(AttributeModifiersComponent component) {
        ArrayList<AttributeModifiersComponent.Entry> gildModifiers = new ArrayList<>(this.attributeModifiers);
        ArrayList<AttributeModifiersComponent.Entry> baseModifiers = new ArrayList<>(component.modifiers());
        ArrayList<AttributeModifiersComponent.Entry> appliedModifiers = new ArrayList<>(baseModifiers);
        for (AttributeModifiersComponent.Entry modifier : gildModifiers) {
            boolean modifiedExisting = false;
            for (AttributeModifiersComponent.Entry modifier1 : baseModifiers) {
                if (modifier1.attribute().equals(modifier.attribute()) && modifier1.slot().equals(modifier.slot())) {
                    modifiedExisting = true;
                    double d = modifier1.modifier().value();
                    double e = modifier.modifier().value();
                    d += switch (modifier.modifier().operation()) {
                        case ADD_VALUE -> e;
                        case ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL -> e * d;
                    };
                    EntityAttributeModifier combinedModifier = new EntityAttributeModifier(modifier1.modifier().id(), d, modifier1.modifier().operation());
                    appliedModifiers.remove(modifier1);
                    appliedModifiers.add(new AttributeModifiersComponent.Entry(modifier1.attribute(), combinedModifier, modifier1.slot(), modifier1.display()));
                }
            }
            if (!modifiedExisting) appliedModifiers.add(modifier);
        }
        return new AttributeModifiersComponent(appliedModifiers);
    }
}

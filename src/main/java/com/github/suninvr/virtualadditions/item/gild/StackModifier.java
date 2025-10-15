package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Predicate;

public abstract class StackModifier<T> {
    private final Predicate<ItemStack> appliesTo;
    protected static final Predicate<ItemStack> ALWAYS_TRUE = stack -> true;
    private final ComponentType<T> componentType;

    public StackModifier(ComponentType<T> componentType, Predicate<ItemStack> appliesTo) {
        this.appliesTo = appliesTo;
        this.componentType = componentType;
    }

    public StackModifier(ComponentType<T> componentType, TagKey<Item> appliesTo) {
        this.appliesTo = isIn(appliesTo);
        this.componentType = componentType;
    }

    public StackModifier(ComponentType<T> componentType) {
        this.appliesTo = ALWAYS_TRUE;
        this.componentType = componentType;
    }

    public static Predicate<ItemStack> isIn(TagKey<Item> itemTag) {
        return stack -> stack.isIn(itemTag);
    }

    public final ItemStack modify(ItemStack stack) {
        if (stack.contains(this.componentType) && appliesTo.test(stack) ) stack.set(this.componentType, this.modifyComponent(stack.get(this.componentType)));
        return stack;
    }

    protected abstract T modifyComponent(T component);
}

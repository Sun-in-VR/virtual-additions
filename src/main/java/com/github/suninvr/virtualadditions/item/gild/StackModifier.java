package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public abstract class StackModifier<T> {
    private final Predicate<ItemStack> appliesTo;
    protected static final Predicate<ItemStack> ALWAYS_TRUE = stack -> true;
    private final DataComponentType<T> componentType;

    public StackModifier(DataComponentType<T> componentType, Predicate<ItemStack> appliesTo) {
        this.appliesTo = appliesTo;
        this.componentType = componentType;
    }

    public StackModifier(DataComponentType<T> componentType, TagKey<Item> appliesTo) {
        this.appliesTo = isIn(appliesTo);
        this.componentType = componentType;
    }

    public StackModifier(DataComponentType<T> componentType) {
        this.appliesTo = ALWAYS_TRUE;
        this.componentType = componentType;
    }

    public static Predicate<ItemStack> isIn(TagKey<Item> itemTag) {
        return stack -> stack.is(itemTag);
    }

    public final ItemStack modify(ItemStack stack) {
        if (stack.has(this.componentType) && appliesTo.test(stack) ) stack.set(this.componentType, this.modifyComponent(stack.get(this.componentType)));
        return stack;
    }

    protected abstract T modifyComponent(T component);
}

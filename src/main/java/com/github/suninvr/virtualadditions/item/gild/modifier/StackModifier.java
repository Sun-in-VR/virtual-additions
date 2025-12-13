package com.github.suninvr.virtualadditions.item.gild.modifier;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public abstract class StackModifier<T> {
    private final DataComponentType<T> componentType;
    private final T defaultComponent;

    public StackModifier(DataComponentType<T> componentType, T defaultComponent) {
        this.componentType = componentType;
        this.defaultComponent = defaultComponent;
    }

    public static Predicate<ItemStack> isIn(TagKey<Item> itemTag) {
        return stack -> stack.is(itemTag);
    }

    public final void modify(DataComponentMap.Builder map) {
        if (!map.contains(this.componentType)) return;
        try {
            T component = map.getOrDefault(this.componentType, this.defaultComponent);
            map.set(this.componentType, this.modifyComponent(component));
        }
        catch (NullPointerException e) {
            VirtualAdditions.LOGGER.error("Skipping modification of data component type: {}", this.componentType);
        }
    }

    protected abstract T modifyComponent(T component);
}

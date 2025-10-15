package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class DurabilityStackModifier extends StackModifier<Integer>{
    float mul;

    public DurabilityStackModifier(float mul) {
        super(DataComponentTypes.MAX_DAMAGE, ALWAYS_TRUE);
        this.mul = mul;
    }

    @Override
    protected Integer modifyComponent(Integer component) {
        return (int) (component * mul);
    }
}

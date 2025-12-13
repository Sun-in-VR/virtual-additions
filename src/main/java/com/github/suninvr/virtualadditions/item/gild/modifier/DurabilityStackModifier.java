package com.github.suninvr.virtualadditions.item.gild.modifier;

import net.minecraft.core.component.DataComponents;

public class DurabilityStackModifier extends StackModifier<Integer> {
    float mul;

    public DurabilityStackModifier(float mul) {
        super(DataComponents.MAX_DAMAGE, 0);
        this.mul = mul;
    }

    @Override
    protected Integer modifyComponent(Integer component) {
        return (int) (component * mul);
    }
}

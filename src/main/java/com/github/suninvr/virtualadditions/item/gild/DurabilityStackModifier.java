package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.core.component.DataComponents;

public class DurabilityStackModifier extends StackModifier<Integer>{
    float mul;

    public DurabilityStackModifier(float mul) {
        super(DataComponents.MAX_DAMAGE, ALWAYS_TRUE);
        this.mul = mul;
    }

    @Override
    protected Integer modifyComponent(Integer component) {
        return (int) (component * mul);
    }
}

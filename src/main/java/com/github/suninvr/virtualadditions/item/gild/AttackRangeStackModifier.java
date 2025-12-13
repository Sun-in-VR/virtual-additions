package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;

import java.util.function.Predicate;

public class AttackRangeStackModifier extends StackModifier<AttackRange> {
    float add;

    public AttackRangeStackModifier(float add) {
        super(DataComponents.ATTACK_RANGE);
        this.add = add;
    }

    @Override
    protected AttackRange modifyComponent(AttackRange component) {

        return new AttackRange(
                component.minRange(),
                component.maxRange() + this.add,
                component.minCreativeRange(),
                component.maxCreativeRange() + this.add,
                component.hitboxMargin(),
                component.mobFactor()
        );
    }
}

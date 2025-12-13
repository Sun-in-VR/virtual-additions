package com.github.suninvr.virtualadditions.item.gild.modifier;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.AttackRange;

public class AttackRangeStackModifier extends StackModifier<AttackRange> {
    private static final AttackRange defaultAttackRange = new AttackRange(0, 0, 0, 0, 0, 0);
    float add;

    public AttackRangeStackModifier(float add) {
        super(DataComponents.ATTACK_RANGE, defaultAttackRange);
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

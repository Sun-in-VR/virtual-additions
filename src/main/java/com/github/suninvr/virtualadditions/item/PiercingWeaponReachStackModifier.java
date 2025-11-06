package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.gild.StackModifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.PiercingWeapon;

public class PiercingWeaponReachStackModifier extends StackModifier<PiercingWeapon> {
    private final float reachAdder;

    public PiercingWeaponReachStackModifier(float reachAdder) {
        super(DataComponents.PIERCING_WEAPON);
        this.reachAdder = reachAdder;
    }

    @Override
    protected PiercingWeapon modifyComponent(PiercingWeapon component) {
        return new PiercingWeapon(
                component.minReach(),
                component.maxReach() + reachAdder,
                component.hitboxMargin(),
                component.dealsKnockback(),
                component.dismounts(),
                component.sound(),
                component.hitSound()
        );
    }
}

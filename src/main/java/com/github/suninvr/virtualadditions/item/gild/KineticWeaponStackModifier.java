package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.KineticWeapon;

import java.util.function.Predicate;

public class KineticWeaponStackModifier extends StackModifier<KineticWeapon>{
    float damageMulAdder = 0.0F;
    float speedMultiplier = 1.0F;
    float reachAdder = 0.0F;

    public KineticWeaponStackModifier(float damageMulAdder, float speedMultiplier, float reachAdder) {
        super(DataComponents.KINETIC_WEAPON);
        this.damageMulAdder = damageMulAdder;
        this.speedMultiplier = speedMultiplier;
        this.reachAdder = reachAdder;
    }

    @Override
    protected KineticWeapon modifyComponent(KineticWeapon component) {
        KineticWeapon kineticWeapon = new KineticWeapon(
                component.minReach(),
                component.maxReach() + this.reachAdder,
                component.hitboxMargin(),
                component.contactCooldownTicks(),
                (int) (component.delayTicks() * (1.0F / this.speedMultiplier)),
                component.dismountConditions(),
                component.knockbackConditions(),
                component.damageConditions(),
                component.forwardMovement(),
                component.damageMultiplier() + this.damageMulAdder,
                component.sound(),
                component.hitSound()
        );
        return kineticWeapon;
    }
}

package com.github.suninvr.virtualadditions.registry.constructors.entity;

import net.minecraft.entity.damage.DamageSource;

public class CustomDamageSource extends net.minecraft.entity.damage.DamageSource {
    protected CustomDamageSource(String name) {
        super(name);
    }

    @Override
    public DamageSource setBypassesArmor() {
        return super.setBypassesArmor();
    }
}

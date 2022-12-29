package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.damage.DamageSource;

public class VADamageSource {
    public static final DamageSource ACID;

    static {
        ACID = new DamageSource("acid").setBypassesArmor();
    }

}

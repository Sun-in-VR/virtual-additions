package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.damage.DamageSource;
import com.github.suninvr.virtualadditions.registry.constructors.entity.CustomDamageSource;

public class VADamageSource {
    public static final DamageSource ACID_BURN = new CustomDamageSource("acid_burn"){}.setBypassesArmor();
}

package com.github.suninvr.virtualadditions.interfaces;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public interface DamageSourcesInterface {
    DamageSource acid();
    DamageSource acidSpit(AcidSpitEntity source, @Nullable Entity attacker);
}

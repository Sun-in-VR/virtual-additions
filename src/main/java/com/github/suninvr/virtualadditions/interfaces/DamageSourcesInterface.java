package com.github.suninvr.virtualadditions.interfaces;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface DamageSourcesInterface {
    DamageSource virtualAdditions$acid();
    DamageSource virtualAdditions$acidSpit(AcidSpitEntity source, @Nullable Entity attacker);
    DamageSource virtualAdditions$soulDestroyed(@Nullable PlayerProjectionEntity source, @Nullable Entity attacker);
}

package com.github.suninvr.virtualadditions.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SilenceAndLoquacityStatusEffect extends InstantStatusEffect {
    boolean loquacity;

    public SilenceAndLoquacityStatusEffect(StatusEffectCategory category, int color, boolean loquacity) {
        super(category, color);
        this.loquacity = loquacity;
    }

    public SilenceAndLoquacityStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
        this.loquacity = false;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) return false;
        if (this.loquacity && entity.isSilent()) entity.setSilent(false);
        else if (!this.loquacity && !entity.isSilent()) entity.setSilent(true);
        return true;
    }
}

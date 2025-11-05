package com.github.suninvr.virtualadditions.entity.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SilenceAndLoquacityStatusEffect extends InstantenousMobEffect {
    boolean loquacity;

    public SilenceAndLoquacityStatusEffect(MobEffectCategory category, int color, boolean loquacity) {
        super(category, color);
        this.loquacity = loquacity;
    }

    public SilenceAndLoquacityStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.loquacity = false;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (entity instanceof Player) return false;
        if (this.loquacity && entity.isSilent()) entity.setSilent(false);
        else if (!this.loquacity && !entity.isSilent()) entity.setSilent(true);
        return true;
    }
}

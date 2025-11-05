package com.github.suninvr.virtualadditions.entity.effect;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class AuraStatusEffect extends MobEffect {
    public AuraStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        List<Entity> entities = world.getEntities(entity, entity.getBoundingBox().inflate(4.0 + (amplifier * 2)));
        for (Entity otherEntity : entities) {
            if (otherEntity instanceof LivingEntity livingEntity) {
                entity.getActiveEffects().forEach(statusEffectInstance -> {
                    if (statusEffectInstance.getEffect() != VAStatusEffects.AURA) livingEntity.addEffect(statusEffectInstance.withScaledDuration(0.25F), entity);
                });
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}

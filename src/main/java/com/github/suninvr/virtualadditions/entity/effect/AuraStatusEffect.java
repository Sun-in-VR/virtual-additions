package com.github.suninvr.virtualadditions.entity.effect;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

import java.util.List;

public class AuraStatusEffect extends StatusEffect {
    public AuraStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        List<Entity> entities = world.getOtherEntities(entity, entity.getBoundingBox().expand(4.0 + (amplifier * 2)));
        for (Entity otherEntity : entities) {
            if (otherEntity instanceof LivingEntity livingEntity) {
                entity.getStatusEffects().forEach(statusEffectInstance -> {
                    if (statusEffectInstance.getEffectType() != VAStatusEffects.AURA) livingEntity.addStatusEffect(statusEffectInstance.withScaledDuration(0.25F), entity);
                });
            }
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}

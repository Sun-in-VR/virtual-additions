package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAStatusEffects {
    public static final StatusEffect IOLITE_INTERFERENCE;

    public static void init(){};

    static {
        IOLITE_INTERFERENCE = Registry.register(Registries.STATUS_EFFECT, idOf("iolite_interference"), new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x9a5bff));
    }

    protected static class CustomStatusEffect extends StatusEffect {
        protected CustomStatusEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }
    }
}

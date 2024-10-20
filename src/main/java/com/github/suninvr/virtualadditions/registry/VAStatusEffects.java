package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAStatusEffects {
    public static final RegistryEntry<StatusEffect> IOLITE_INTERFERENCE;
    public static final RegistryEntry<StatusEffect> FRAILTY;
    public static final RegistryEntry<StatusEffect> LOVE;

    static {
        IOLITE_INTERFERENCE = Registry.registerReference(Registries.STATUS_EFFECT, idOf("iolite_interference"), new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x9a5bff, VAParticleTypes.INTERFERENCE));
        FRAILTY = Registry.registerReference(Registries.STATUS_EFFECT, idOf("frailty"), new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0xe9E4021));
        LOVE = Registry.registerReference(Registries.STATUS_EFFECT, idOf("love"), new CustomStatusEffect(StatusEffectCategory.NEUTRAL, 0xdfa7e5));
    }

    public static void init(){}

    protected static class CustomStatusEffect extends StatusEffect {
        protected CustomStatusEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }

        protected CustomStatusEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
            super(category, color, particleEffect);
        }
    }
}

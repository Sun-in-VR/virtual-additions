package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.entity.effect.AuraStatusEffect;
import com.github.suninvr.virtualadditions.entity.effect.SilenceAndLoquacityStatusEffect;
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
    public static final RegistryEntry<StatusEffect> SILENCE;
    public static final RegistryEntry<StatusEffect> LOQUACITY;
    public static final RegistryEntry<StatusEffect> AURA;

    static {
        IOLITE_INTERFERENCE = Registry.registerReference(Registries.STATUS_EFFECT, idOf("iolite_interference"), new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x9a5bff, VAParticleTypes.INTERFERENCE));
        FRAILTY = Registry.registerReference(Registries.STATUS_EFFECT, idOf("frailty"), new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0xe9E4021));
        LOVE = Registry.registerReference(Registries.STATUS_EFFECT, idOf("love"), new CustomStatusEffect(StatusEffectCategory.NEUTRAL, 0xdfa7e5));
        SILENCE = Registry.registerReference(Registries.STATUS_EFFECT, idOf("silence"), new SilenceAndLoquacityStatusEffect(StatusEffectCategory.NEUTRAL, 0x1987a1));
        LOQUACITY = Registry.registerReference(Registries.STATUS_EFFECT, idOf("loquacity"), new SilenceAndLoquacityStatusEffect(StatusEffectCategory.NEUTRAL, 0xe6785e, true));
        AURA = Registry.registerReference(Registries.STATUS_EFFECT, idOf("aura"), new AuraStatusEffect(StatusEffectCategory.NEUTRAL, 0x9dbad0));
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

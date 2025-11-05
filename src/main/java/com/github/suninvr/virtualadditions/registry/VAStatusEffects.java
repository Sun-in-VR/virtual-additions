package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.entity.effect.AuraStatusEffect;
import com.github.suninvr.virtualadditions.entity.effect.SilenceAndLoquacityStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAStatusEffects {
    public static final Holder<MobEffect> IOLITE_INTERFERENCE;
    public static final Holder<MobEffect> FRAILTY;
    public static final Holder<MobEffect> LOVE;
    public static final Holder<MobEffect> SILENCE;
    public static final Holder<MobEffect> LOQUACITY;
    public static final Holder<MobEffect> AURA;
    public static final Holder<MobEffect> FESTERING_WOUNDS;

    static {
        IOLITE_INTERFERENCE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("iolite_interference"), new CustomStatusEffect(MobEffectCategory.HARMFUL, 0x9a5bff, VAParticleTypes.INTERFERENCE));
        FRAILTY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("frailty"), new CustomStatusEffect(MobEffectCategory.HARMFUL, 0xe9E4021));
        LOVE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("love"), new CustomStatusEffect(MobEffectCategory.NEUTRAL, 0xdfa7e5));
        SILENCE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("silence"), new SilenceAndLoquacityStatusEffect(MobEffectCategory.NEUTRAL, 0x1987a1));
        LOQUACITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("loquacity"), new SilenceAndLoquacityStatusEffect(MobEffectCategory.NEUTRAL, 0xe6785e, true));
        AURA = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("aura"), new AuraStatusEffect(MobEffectCategory.NEUTRAL, 0x9dbad0));
        FESTERING_WOUNDS = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, idOf("festering_wounds"), new CustomStatusEffect(MobEffectCategory.HARMFUL, 0x1987a1, VAParticleTypes.STATIC_SCULK_CHARGE_POP));
    }

    public static void init(){}

    protected static class CustomStatusEffect extends MobEffect {
        protected CustomStatusEffect(MobEffectCategory category, int color) {
            super(category, color);
        }

        protected CustomStatusEffect(MobEffectCategory category, int color, ParticleOptions particleEffect) {
            super(category, color, particleEffect);
        }
    }
}

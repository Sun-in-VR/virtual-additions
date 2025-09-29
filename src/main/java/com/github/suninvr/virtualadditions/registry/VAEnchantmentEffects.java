package com.github.suninvr.virtualadditions.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.UnaryOperator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEnchantmentEffects {
    public static final RegistryKey<Enchantment> STEADFAST = RegistryKey.of(RegistryKeys.ENCHANTMENT, idOf("steadfast"));
    public static final ComponentType<EnchantmentValueEffect> HALBERD_SWING_COOLDOWN_COMPONENT = registerComponent(
            "halberd_swing_cooldown", builder -> builder.codec(EnchantmentValueEffect.CODEC)
    );
    public static final ComponentType<EnchantmentValueEffect> HALBERD_READINESS_TIME_COMPONENT = registerComponent(
            "halberd_readiness_time", builder -> builder.codec(EnchantmentValueEffect.CODEC)
    );
    public static final ComponentType<EnchantmentValueEffect> HALBERD_LUNGE_COMPONENT = registerComponent(
            "halberd_lunge", builder -> builder.codec(EnchantmentValueEffect.CODEC)
    );

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, idOf(id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void init() {
    }
}

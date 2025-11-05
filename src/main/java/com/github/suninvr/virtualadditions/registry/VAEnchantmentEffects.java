package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

import java.util.function.UnaryOperator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEnchantmentEffects {
    public static final ResourceKey<Enchantment> STEADFAST = ResourceKey.create(Registries.ENCHANTMENT, idOf("steadfast"));
    public static final DataComponentType<EnchantmentValueEffect> HALBERD_SWING_COOLDOWN_COMPONENT = registerComponent(
            "halberd_swing_cooldown", builder -> builder.persistent(EnchantmentValueEffect.CODEC)
    );
    public static final DataComponentType<EnchantmentValueEffect> HALBERD_READINESS_TIME_COMPONENT = registerComponent(
            "halberd_readiness_time", builder -> builder.persistent(EnchantmentValueEffect.CODEC)
    );
    public static final DataComponentType<EnchantmentValueEffect> HALBERD_LUNGE_COMPONENT = registerComponent(
            "halberd_lunge", builder -> builder.persistent(EnchantmentValueEffect.CODEC)
    );

    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, idOf(id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void init() {
    }
}

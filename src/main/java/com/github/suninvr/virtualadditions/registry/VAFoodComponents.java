package com.github.suninvr.virtualadditions.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class VAFoodComponents {
    public static final FoodProperties FRIED_EGG = (new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build());
    public static final FoodProperties CHEESE_WEDGE = (new FoodProperties.Builder().nutrition(5).saturationModifier(0.4F).build());
    public static final FoodProperties TOMATO = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.4F).build();
    public static final FoodProperties TOMATO_SOUP = (new FoodProperties.Builder()).nutrition(8).saturationModifier(0.6F).build();
    public static final FoodProperties CABBAGE = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodProperties CORN = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final FoodProperties ROASTED_CORN = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodProperties TORTILLA = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.4F).build();
    public static final FoodProperties WISDOM_BERRY = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.1F).build();
    public static final FoodProperties SALAD = (new FoodProperties.Builder()).nutrition(16).saturationModifier(1.5F).build();
    public static final FoodProperties TACO = (new FoodProperties.Builder()).nutrition(8).saturationModifier(1.8F).build();
    public static final FoodProperties ICE_CREAM = new FoodProperties.Builder().nutrition(7).saturationModifier(0.1F).build();
    public static final FoodProperties BALLOON_FRUIT = (new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).alwaysEdible().build());
    public static final FoodProperties SWEET_BERRY_PIE = (new FoodProperties.Builder().nutrition(8).saturationModifier(0.3F).build());
    public static final FoodProperties ROCK_SALT = (new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build());
    public static final FoodProperties BEEF_JERKY = (new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build());
    public static final FoodProperties PORK_JERKY = (new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build());
    public static final FoodProperties CHICKEN_JERKY = (new FoodProperties.Builder().nutrition(3).saturationModifier(0.45F).build());
    public static final FoodProperties MUTTON_JERKY = (new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build());

    public static final Consumable JERKY_CONSUMABLE = Consumable.builder().consumeSeconds(1.2F).build();
    public static final Consumable BALLOON_FRUIT_CONSUMABLE = Consumable.builder().onConsume(
            new ApplyStatusEffectsConsumeEffect(
                    List.of(
                            new MobEffectInstance(MobEffects.LEVITATION, 100)
                    )
            )
    ).build();
    public static final Consumable ROCK_SALT_CONSUMABLE = Consumable.builder().onConsume(
            new ApplyStatusEffectsConsumeEffect(
                    List.of(
                            new MobEffectInstance(VAStatusEffects.FRAILTY, 600, 1),
                            new MobEffectInstance(MobEffects.WEAKNESS, 600),
                            new MobEffectInstance(MobEffects.NAUSEA, 200)
                    )))
            .build();
}

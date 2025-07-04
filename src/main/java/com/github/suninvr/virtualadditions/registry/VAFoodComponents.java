package com.github.suninvr.virtualadditions.registry;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

public class VAFoodComponents {
    public static final FoodComponent FRIED_EGG = (new FoodComponent.Builder().nutrition(4).saturationModifier(0.3F).build());
    public static final FoodComponent CHEESE_WEDGE = (new FoodComponent.Builder().nutrition(5).saturationModifier(0.4F).build());
    public static final FoodComponent TOMATO = (new FoodComponent.Builder()).nutrition(3).saturationModifier(0.4F).build();
    public static final FoodComponent CABBAGE = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodComponent CORN = (new FoodComponent.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final FoodComponent ROASTED_CORN = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodComponent WISBERRY = (new FoodComponent.Builder()).nutrition(2).saturationModifier(0.1F).build();
    public static final FoodComponent ICE_CREAM = new FoodComponent.Builder().nutrition(7).saturationModifier(0.1F).build();
    public static final FoodComponent BALLOON_FRUIT = (new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).alwaysEdible().build());
    public static final FoodComponent SWEET_BERRY_PIE = (new FoodComponent.Builder().nutrition(8).saturationModifier(0.3F).build());
    public static final FoodComponent ROCK_SALT = (new FoodComponent.Builder().nutrition(1).saturationModifier(0.1F).build());
    public static final FoodComponent BEEF_JERKY = (new FoodComponent.Builder().nutrition(4).saturationModifier(0.6F).build());
    public static final FoodComponent PORK_JERKY = (new FoodComponent.Builder().nutrition(4).saturationModifier(0.6F).build());
    public static final FoodComponent CHICKEN_JERKY = (new FoodComponent.Builder().nutrition(3).saturationModifier(0.45F).build());
    public static final FoodComponent MUTTON_JERKY = (new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build());

    public static final ConsumableComponent JERKY_CONSUMABLE = ConsumableComponent.builder().consumeSeconds(1.2F).build();
    public static final ConsumableComponent BALLOON_FRUIT_CONSUMABLE = ConsumableComponent.builder().consumeEffect(
            new ApplyEffectsConsumeEffect(
                    List.of(
                            new StatusEffectInstance(StatusEffects.LEVITATION, 100)
                    )
            )
    ).build();
    public static final ConsumableComponent ROCK_SALT_CONSUMABLE = ConsumableComponent.builder().consumeEffect(
            new ApplyEffectsConsumeEffect(
                    List.of(
                            new StatusEffectInstance(VAStatusEffects.FRAILTY, 600, 1),
                            new StatusEffectInstance(StatusEffects.WEAKNESS, 600),
                            new StatusEffectInstance(StatusEffects.NAUSEA, 200)
                    )))
            .build();
}

package com.github.suninvr.virtualadditions.registry;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

public class VAFoodComponents {
    public static final FoodComponent FRIED_EGG = (new FoodComponent.Builder().nutrition(4).saturationModifier(0.4F).build());
    public static final FoodComponent CHEESE_WEDGE = (new FoodComponent.Builder().nutrition(5).saturationModifier(0.4F).build());
    public static final FoodComponent CORN = (new FoodComponent.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final FoodComponent ROASTED_CORN = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodComponent ICE_CREAM = new FoodComponent.Builder().nutrition(7).saturationModifier(0.1F).build();
    public static final FoodComponent BALLOON_FRUIT = (new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).alwaysEdible().build());
    public static final FoodComponent SWEET_BERRY_PIE = (new FoodComponent.Builder().nutrition(8).saturationModifier(0.3F).build());
    public static final FoodComponent ROCK_SALT = (new FoodComponent.Builder().nutrition(1).saturationModifier(0.1F).build());

    public static final FoodComponent SALTED_ROASTED_CORN = createSaltedFood(ROASTED_CORN);
    public static final FoodComponent SALTED_COOKED_BEEF = createSaltedFood(FoodComponents.COOKED_BEEF);
    public static final FoodComponent SALTED_COOKED_PORKCHOP = createSaltedFood(FoodComponents.COOKED_PORKCHOP);
    public static final FoodComponent SALTED_COOKED_CHICKEN = createSaltedFood(FoodComponents.COOKED_CHICKEN);
    public static final FoodComponent SALTED_COOKED_MUTTON = createSaltedFood(FoodComponents.COOKED_MUTTON);

    public static final ConsumableComponent ROCK_SALT_CONSUMABLE = ConsumableComponent.builder().consumeEffect(
            new ApplyEffectsConsumeEffect(
                    List.of(
                            new StatusEffectInstance(VAStatusEffects.FRAILTY, 600, 1),
                            new StatusEffectInstance(StatusEffects.WEAKNESS, 600),
                            new StatusEffectInstance(StatusEffects.NAUSEA, 200)
                    )))
            .build();

    private static FoodComponent createSaltedFood(FoodComponent component) {
        FoodComponent.Builder builder = new FoodComponent.Builder().nutrition(component.nutrition() + 1).saturationModifier(Math.max(0.1F, component.saturation() - 0.5F));
        if (component.canAlwaysEat()) builder = builder.alwaysEdible();
        return builder.build();
    }
}

package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryAccessor {

    @Invoker("registerItemRecipe")
    static void virtualAdditions$registerCustomItemRecipe(Item input, Item ingredient, Item output) {
        throw new AssertionError();
    }

    @Invoker("registerPotionType")
    static void virtualAdditions$registerCustomPotionType(Item item) {
        throw new AssertionError();
    }

    @Invoker("registerPotionRecipe")
    static void virtualAdditions$registerCustomPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }
}

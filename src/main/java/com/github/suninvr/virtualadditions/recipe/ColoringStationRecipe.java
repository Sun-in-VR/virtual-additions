package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.DynamicRegistryManager;

import java.util.Optional;

public interface ColoringStationRecipe extends Recipe<RecipeInput> {
    int getIndex();

    Optional<Ingredient> getIngredient();

    SlotDisplay.StackSlotDisplay getStackSlotDisplay();

    ItemStack craftWithDye(RecipeInput input, DynamicRegistryManager registryManager, DyeContents dyeContents);

    DyeContents getDyeCost(boolean inverted);

    default DyeContents getDyeCost() {
        return this.getDyeCost(false);
    }

    ItemStack getResultStack(DynamicRegistryManager registryManager, ItemStack input);

    ItemStack getResultStack(ItemStack input);
}

package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import net.minecraft.inventory.Inventory;
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

    ItemStack craftWithDye(RecipeInput input, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents dyeContents);

    ColoringStationBlockEntity.DyeContents getDyeCost(boolean inverted);

    default ColoringStationBlockEntity.DyeContents getDyeCost() {
        return this.getDyeCost(false);
    }

    ItemStack getResultStack(DynamicRegistryManager registryManager, ItemStack input);

    ItemStack getResultStack(ItemStack input);
}

package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.Optional;

public interface ColoringStationRecipe extends Recipe<RecipeInput> {
    int getIndex();

    Optional<Ingredient> getIngredient();

    SlotDisplay.ItemStackSlotDisplay getStackSlotDisplay();

    ItemStack craftWithDye(RecipeInput input, RegistryAccess registryManager, DyeContents dyeContents);

    DyeContents getDyeCost(boolean inverted);

    default DyeContents getDyeCost() {
        return this.getDyeCost(false);
    }

    ItemStack getResultStack(RegistryAccess registryManager, ItemStack input);

    ItemStack getResultStack(ItemStack input);
}

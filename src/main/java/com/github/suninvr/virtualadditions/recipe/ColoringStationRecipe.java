package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;

public interface ColoringStationRecipe extends Recipe<Inventory> {
    int getIndex();

    ItemStack craftWithDye(Inventory input, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents dyeContents);

    ColoringStationBlockEntity.DyeContents getDyeCost(boolean inverted);

    default ColoringStationBlockEntity.DyeContents getDyeCost() {
        return this.getDyeCost(false);
    }

    ItemStack getResultStack(DynamicRegistryManager registryManager, ItemStack input);
}

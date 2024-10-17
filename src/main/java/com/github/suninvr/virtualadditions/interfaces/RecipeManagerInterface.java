package com.github.suninvr.virtualadditions.interfaces;

import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipeDisplay;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;

public interface RecipeManagerInterface {
    public ColoringRecipeDisplay.Grouping<ColoringStationRecipe> virtualAdditions$getColoringRecipes();
}

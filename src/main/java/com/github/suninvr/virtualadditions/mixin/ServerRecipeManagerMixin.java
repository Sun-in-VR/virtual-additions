package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.RecipeManagerInterface;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipeDisplay;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(RecipeManager.class)
public class ServerRecipeManagerMixin implements RecipeManagerInterface {
    @Shadow private RecipeMap recipes;
    @Unique private ColoringRecipeDisplay.Grouping<ColoringStationRecipe> coloringRecipes = ColoringRecipeDisplay.Grouping.empty();

    @Inject(method = "finalizeRecipeLoading", at = @At("TAIL"))
    void virtualAdditions$initializeColoringRecipes(FeatureFlagSet features, CallbackInfo ci) {
        List<ColoringRecipeDisplay.GroupEntry<ColoringStationRecipe>> recipes = new ArrayList<>();
        this.recipes.values().forEach(recipeEntry -> {
            Recipe<?> recipe = recipeEntry.value();
            if (recipe instanceof ColoringStationRecipe coloringRecipe) {
                RecipeHolder<ColoringStationRecipe> coloringRecipeEntry = (RecipeHolder<ColoringStationRecipe>) recipeEntry;
                recipes.add(new ColoringRecipeDisplay.GroupEntry<>(
                        coloringRecipe.getIngredient(),
                        new ColoringRecipeDisplay<>(coloringRecipe.getStackSlotDisplay(), Optional.of(coloringRecipeEntry))
                        ));
            }
        });
        this.coloringRecipes = new ColoringRecipeDisplay.Grouping<>(recipes);
    }

    @Override
    public ColoringRecipeDisplay.Grouping<ColoringStationRecipe> virtualAdditions$getColoringRecipes() {
        return this.coloringRecipes;
    }
}

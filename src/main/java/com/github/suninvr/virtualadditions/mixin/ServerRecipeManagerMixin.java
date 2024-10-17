package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.RecipeManagerInterface;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipeDisplay;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin implements RecipeManagerInterface {
    @Shadow private PreparedRecipes preparedRecipes;
    @Unique private ColoringRecipeDisplay.Grouping<ColoringStationRecipe> coloringRecipes = ColoringRecipeDisplay.Grouping.empty();

    @Inject(method = "initialize", at = @At("TAIL"))
    void virtualAdditions$initializeColoringRecipes(FeatureSet features, CallbackInfo ci) {
        List<ColoringRecipeDisplay.GroupEntry<ColoringStationRecipe>> recipes = new ArrayList<>();
        this.preparedRecipes.recipes().forEach(recipeEntry -> {
            Recipe<?> recipe = recipeEntry.value();
            if (recipe instanceof ColoringStationRecipe coloringRecipe) {
                RecipeEntry<ColoringStationRecipe> coloringRecipeEntry = (RecipeEntry<ColoringStationRecipe>) recipeEntry;
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

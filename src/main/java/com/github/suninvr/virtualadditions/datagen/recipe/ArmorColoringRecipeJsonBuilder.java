package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

public class ArmorColoringRecipeJsonBuilder {
    private final Ingredient dye;
    private final int index;

    public ArmorColoringRecipeJsonBuilder(@Nullable Ingredient input, int index) {
        this.dye = input;
        this.index = index;
    }

    public static ArmorColoringRecipeJsonBuilder create(@Nullable Ingredient input, int index) {
        return new ArmorColoringRecipeJsonBuilder(input, index);
    }

    public void offerTo(RecipeOutput exporter, ResourceLocation recipeId) {
        if (this.dye == null) return;
        ResourceKey<Recipe<?>> registryKey = ResourceKey.create(Registries.RECIPE, recipeId);
        ArmorColoringRecipe recipe = new ArmorColoringRecipe(this.dye, this.index);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(registryKey)).rewards(AdvancementRewards.Builder.recipe(registryKey)).requirements(AdvancementRequirements.Strategy.OR);
        exporter.accept(registryKey, recipe, builder.build(recipeId.withPrefix("recipes/coloring/")));
    }
}

package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ColoringRecipeJsonBuilder {
    private final DyeContents dyeCost;
    private final Ingredient input;
    private final Item output;
    private final int index;
    
    public ColoringRecipeJsonBuilder(Ingredient input, DyeContents dyeCost, ItemConvertible output, int index) {
        this.input = input;
        this.dyeCost = dyeCost;
        this.output = output.asItem();
        this.index = index;
    }
    
    public static ColoringRecipeJsonBuilder create(@Nullable Ingredient input, DyeContents dyeCost, ItemConvertible output, int index) {
        return new ColoringRecipeJsonBuilder(input, dyeCost, output, index);
    }

    public void offerTo(RecipeExporter exporter) {
        offerTo(exporter, Registries.ITEM.getId(this.output).withSuffixedPath("_from_coloring"));
    }
    
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        RegistryKey<Recipe<?>> registryKey = RegistryKey.of(RegistryKeys.RECIPE, recipeId);
        ColoringRecipe coloringRecipe = new ColoringRecipe(Optional.ofNullable(this.input), new ItemStack(this.output), this.dyeCost, this.index);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(registryKey)).rewards(AdvancementRewards.Builder.recipe(registryKey)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        exporter.accept(registryKey, coloringRecipe, builder.build(recipeId.withPrefixedPath("recipes/")));
    }
}

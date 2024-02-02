package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
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
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ColoringRecipeJsonBuilder {
    private final ColoringStationBlockEntity.DyeContents dyeCost;
    private final Ingredient input;
    private final Item output;
    private final int index;
    
    public ColoringRecipeJsonBuilder(@Nullable Ingredient input, ColoringStationBlockEntity.DyeContents dyeCost, ItemConvertible output, int index) {
        this.input = input == null ? Ingredient.EMPTY : input;
        this.dyeCost = dyeCost;
        this.output = output.asItem();
        this.index = index;
    }
    
    public static ColoringRecipeJsonBuilder create(@Nullable Ingredient input, ColoringStationBlockEntity.DyeContents dyeCost, ItemConvertible output, int index) {
        return new ColoringRecipeJsonBuilder(input, dyeCost, output, index);
    }

    public void offerTo(RecipeExporter exporter) {
        offerTo(exporter, Registries.ITEM.getId(this.output).withSuffixedPath("_from_coloring"));
    }
    
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        ColoringRecipe recipe = new ColoringRecipe(this.input, new ItemStack(this.output), this.dyeCost, this.index);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/")));
    }
}

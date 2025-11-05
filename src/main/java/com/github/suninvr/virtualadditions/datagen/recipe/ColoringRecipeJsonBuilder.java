package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ColoringRecipeJsonBuilder {
    private final DyeContents dyeCost;
    private final Ingredient input;
    private final Item output;
    private final int index;
    
    public ColoringRecipeJsonBuilder(Ingredient input, DyeContents dyeCost, ItemLike output, int index) {
        this.input = input;
        this.dyeCost = dyeCost;
        this.output = output.asItem();
        this.index = index;
    }
    
    public static ColoringRecipeJsonBuilder create(@Nullable Ingredient input, DyeContents dyeCost, ItemLike output, int index) {
        return new ColoringRecipeJsonBuilder(input, dyeCost, output, index);
    }

    public void offerTo(RecipeOutput exporter) {
        offerTo(exporter, BuiltInRegistries.ITEM.getKey(this.output).withSuffix("_from_coloring"));
    }
    
    public void offerTo(RecipeOutput exporter, ResourceLocation recipeId) {
        ResourceKey<Recipe<?>> registryKey = ResourceKey.create(Registries.RECIPE, recipeId);
        ColoringRecipe coloringRecipe = new ColoringRecipe(Optional.ofNullable(this.input), new ItemStack(this.output), this.dyeCost, this.index);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(registryKey)).rewards(AdvancementRewards.Builder.recipe(registryKey)).requirements(AdvancementRequirements.Strategy.OR);
        exporter.accept(registryKey, coloringRecipe, builder.build(recipeId.withPrefix("recipes/coloring/")));
    }
}

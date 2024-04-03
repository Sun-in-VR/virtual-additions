package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.DyeItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class ArmorColoringRecipeJsonBuilder {
    private final Ingredient dye;
    private final int index;

    public ArmorColoringRecipeJsonBuilder(@Nullable Ingredient input, int index) {
        this.dye = input == null ? Ingredient.EMPTY : input;
        this.index = index;
    }

    public static ArmorColoringRecipeJsonBuilder create(@Nullable Ingredient input, int index) {
        return new ArmorColoringRecipeJsonBuilder(input, index);
    }

    public void offerTo(RecipeExporter exporter) {
        if (this.dye.getMatchingStacks()[0].getItem() instanceof DyeItem dyeItem) {
            Identifier id = idOf(dyeItem.getColor().asString()).withSuffixedPath("_armor_coloring");
            offerTo(exporter, id);
        }
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        ArmorColoringRecipe recipe = new ArmorColoringRecipe(this.dye, this.index);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/")));
    }
}

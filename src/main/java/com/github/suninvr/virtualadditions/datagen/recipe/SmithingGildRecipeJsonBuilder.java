package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.recipe.SmithingGildRecipe;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.SmithingTrimRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmithingGildRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final RegistryEntry<GildType> gildType;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap();

    public SmithingGildRecipeJsonBuilder(RecipeCategory category, Ingredient base, Ingredient addition, RegistryEntry<GildType> gildType) {
        this.category = category;
        this.base = base;
        this.addition = addition;
        this.gildType = gildType;
    }

    public static SmithingGildRecipeJsonBuilder create(Ingredient base, Ingredient addition, RegistryEntry<GildType> gildType) {
        return new SmithingGildRecipeJsonBuilder(RecipeCategory.TOOLS, base, addition, gildType);
    }

    public SmithingGildRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeKeyId) {
        this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, recipeKeyId));
    }

    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        SmithingGildRecipe recipe = new SmithingGildRecipe(this.base, this.addition, this.gildType);
        exporter.accept(recipeKey, recipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }
}

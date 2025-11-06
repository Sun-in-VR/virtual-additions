package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.recipe.SmithingGildRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmithingGildRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final Holder<GildType> gildType;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();

    public SmithingGildRecipeJsonBuilder(RecipeCategory category, Ingredient base, Ingredient addition, Holder<GildType> gildType) {
        this.category = category;
        this.base = base;
        this.addition = addition;
        this.gildType = gildType;
    }

    public static SmithingGildRecipeJsonBuilder create(Ingredient base, Ingredient addition, Holder<GildType> gildType) {
        return new SmithingGildRecipeJsonBuilder(RecipeCategory.TOOLS, base, addition, gildType);
    }

    public SmithingGildRecipeJsonBuilder criterion(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeOutput exporter, Identifier recipeKeyId) {
        this.offerTo(exporter, ResourceKey.create(Registries.RECIPE, recipeKeyId));
    }

    public void offerTo(RecipeOutput exporter, ResourceKey<Recipe<?>> recipeKey) {
        Advancement.Builder builder = exporter.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        SmithingGildRecipe recipe = new SmithingGildRecipe(this.base, this.addition, this.gildType);
        exporter.accept(recipeKey, recipe, builder.build(recipeKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }
}

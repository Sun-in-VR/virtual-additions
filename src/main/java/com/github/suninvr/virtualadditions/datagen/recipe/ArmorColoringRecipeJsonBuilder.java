package com.github.suninvr.virtualadditions.datagen.recipe;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

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

    public void offerTo(RecipeExporter exporter) {
        if (this.dye == null) return;
        RegistryKey<Item> dye = this.dye.getMatchingItems().getFirst().getKey().get();
        if (dye != null && Registries.ITEM.get(dye) instanceof DyeItem dyeItem) {
            Identifier id = idOf(dyeItem.getColor().asString()).withSuffixedPath("_armor_coloring");
            offerTo(exporter, id);
        }
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        if (this.dye == null) return;
        RegistryKey<Recipe<?>> registryKey = RegistryKey.of(RegistryKeys.RECIPE, recipeId);
        ArmorColoringRecipe recipe = new ArmorColoringRecipe(this.dye, this.index);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(registryKey)).rewards(AdvancementRewards.Builder.recipe(registryKey)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        exporter.accept(registryKey, recipe, builder.build(recipeId.withPrefixedPath("recipes/")));
    }
}

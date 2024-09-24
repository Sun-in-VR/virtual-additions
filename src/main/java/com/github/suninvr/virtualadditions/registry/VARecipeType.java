package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARecipeType {
    public static final RecipeType<ColoringStationRecipe> COLORING = register("coloring");
    public static final RecipeSerializer<ColoringRecipe> COLORING_SERIALIZER = RecipeSerializer.register(idOf("coloring").toString(), new ColoringRecipe.Serializer());
    public static final RecipeSerializer<ArmorColoringRecipe> ARMOR_COLORING_SERIALIZER = RecipeSerializer.register(idOf("armor_coloring").toString(), new ArmorColoringRecipe.Serializer());

    public static void init(){}

    static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        return Registry.register(Registries.RECIPE_TYPE, idOf(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }
}

package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.recipe.SmithingGildRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARecipeType {
    public static final RecipeType<ColoringStationRecipe> COLORING = register("coloring");
    public static final RecipeSerializer<ColoringRecipe> COLORING_SERIALIZER = RecipeSerializer.register(idOf("coloring").toString(), new ColoringRecipe.Serializer());
    public static final RecipeSerializer<ArmorColoringRecipe> ARMOR_COLORING_SERIALIZER = RecipeSerializer.register(idOf("armor_coloring").toString(), new ArmorColoringRecipe.Serializer());
    public static final RecipeSerializer<SmithingGildRecipe> SMITHING_GILD_SERIALIZER = RecipeSerializer.register(idOf("smithing_gild").toString(), new SmithingGildRecipe.Serializer());


    public static void init(){}

    static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, idOf(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }
}

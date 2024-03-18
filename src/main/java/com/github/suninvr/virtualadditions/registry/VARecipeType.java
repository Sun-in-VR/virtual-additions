package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.recipe.ArmorColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARecipeType {
    public static final RecipeType<ColoringStationRecipe> COLORING = RecipeType.register("coloring");
    public static final RecipeSerializer<ColoringRecipe> COLORING_SERIALIZER = RecipeSerializer.register(idOf("coloring").toString(), new ColoringRecipe.Serializer());
    public static final RecipeSerializer<ArmorColoringRecipe> ARMOR_COLORING_SERIALIZER = RecipeSerializer.register(idOf("armor_coloring").toString(), new ArmorColoringRecipe.Serializer());

    public static void init(){}
}

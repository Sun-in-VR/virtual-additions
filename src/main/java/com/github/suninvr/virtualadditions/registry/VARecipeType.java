package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARecipeType {
    public static final RecipeType<ColoringRecipe> COLORING = RecipeType.register("coloring");
    public static final RecipeSerializer<ColoringRecipe> COLORING_SERIALIZER = RecipeSerializer.register(idOf("coloring").toString(), new ColoringRecipe.Serializer());

    public static void init(){}
}

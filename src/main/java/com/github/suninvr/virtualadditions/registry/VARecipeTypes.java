package com.github.suninvr.virtualadditions.registry;

import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.registry.Registry;
import com.github.suninvr.virtualadditions.recipe.WorkbenchDoubleRecipe;
import com.github.suninvr.virtualadditions.recipe.WorkbenchSingleRecipe;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARecipeTypes {

    public static RecipeType<WorkbenchSingleRecipe> WORKBENCH_SINGLE_RECIPE;
    public static RecipeType<WorkbenchDoubleRecipe> WORKBENCH_DOUBLE_RECIPE;
    public static RecipeBookCategory WORKBENCH_CATEGORY;

    public static void init(){

        Registry.register(Registry.RECIPE_SERIALIZER, WorkbenchSingleRecipe.WorkbenchRecipeSerializer.ID, WorkbenchSingleRecipe.WorkbenchRecipeSerializer.INSTANCE);
        WORKBENCH_SINGLE_RECIPE = Registry.register(Registry.RECIPE_TYPE, idOf(WorkbenchSingleRecipe.Type.ID), WorkbenchSingleRecipe.Type.INSTANCE);

        Registry.register(Registry.RECIPE_SERIALIZER, WorkbenchDoubleRecipe.WorkbenchRecipeSerializer.ID, WorkbenchDoubleRecipe.WorkbenchRecipeSerializer.INSTANCE);
        WORKBENCH_DOUBLE_RECIPE = Registry.register(Registry.RECIPE_TYPE, idOf(WorkbenchDoubleRecipe.Type.ID), WorkbenchDoubleRecipe.Type.INSTANCE);

    }
}

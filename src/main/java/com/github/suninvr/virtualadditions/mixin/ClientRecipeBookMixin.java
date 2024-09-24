package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VARecipeType;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void virtualAddtions$shutThisFuckingLogUp(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        RecipeType<?> type = recipe.value().getType();
        if (type == VARecipeType.COLORING) cir.setReturnValue(RecipeBookGroup.UNKNOWN);
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipyRegistryMixin {


    @Mixin(BrewingRecipeRegistry.Builder.class)
    public static class BuilderMixin {
        @Inject(method = "assertPotion", at = @At("HEAD"), cancellable = true)
        private static void virtualAdditions$skipAssert(Item potionType, CallbackInfo ci) {
            if (VirtualAdditions.skipBrewingRecipeAssert) ci.cancel();
        }
    }

    @Inject(method = "craft", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$craftExperienceBottleFromWisdomBerry(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (ingredient.isOf(VAItems.WISDOM_BERRY)) cir.setReturnValue(Items.EXPERIENCE_BOTTLE.getDefaultStack());
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class BrewingRecipyRegistryMixin {


    @Mixin(PotionBrewing.Builder.class)
    public static class BuilderMixin {
        @Inject(method = "expectPotion", at = @At("HEAD"), cancellable = true)
        private static void virtualAdditions$skipAssert(Item potionType, CallbackInfo ci) {
            if (VirtualAdditions.skipBrewingRecipeAssert) ci.cancel();
        }
    }

    @Inject(method = "mix", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$craftExperienceBottleFromWisdomBerry(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (ingredient.is(VAItems.WISDOM_BERRY) && input.get(DataComponents.POTION_CONTENTS).is(Potions.WATER)) cir.setReturnValue(Items.EXPERIENCE_BOTTLE.getDefaultInstance());
    }
}

package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.class)
public class BundleContentsComponentMixin {
    @Inject(method = "getWeight", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getOccupancyForNonStackableItem(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        if (stack.getMaxStackSize() <= 1 && !stack.is(ItemTags.BUNDLES)) cir.setReturnValue(Fraction.ONE_QUARTER);
    }
}

package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContentsComponent.class)
public class BundleContentsComponentMixin {
    @Inject(method = "getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getOccupancyForNonStackableItem(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        if (stack.getMaxCount() <= 1 && !stack.isIn(ItemTags.BUNDLES)) cir.setReturnValue(Fraction.ONE_QUARTER);
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {
    private static final Predicate<ItemStack> climbingRopePredicate = (itemStack -> itemStack.isOf(VAItems.CLIMBING_ROPE));

    @Inject(at = @At("HEAD"), method = "getHeldProjectile", cancellable = true)
    private static void virtualAdditions$getHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
        if(predicate.equals(RangedWeaponItem.CROSSBOW_HELD_PROJECTILES)) {
            if (climbingRopePredicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
                cir.setReturnValue(entity.getStackInHand(Hand.OFF_HAND));
            } else if (climbingRopePredicate.test(entity.getStackInHand(Hand.MAIN_HAND))){
                cir.setReturnValue(entity.getStackInHand(Hand.MAIN_HAND));
            }
        }
    }
}

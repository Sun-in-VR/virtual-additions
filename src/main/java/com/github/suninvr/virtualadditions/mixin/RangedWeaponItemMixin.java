package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {
    @Unique
    private static final Predicate<ItemStack> climbingRopePredicate = (itemStack -> itemStack.isIn(VAItemTags.CLIMBING_ROPES));

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

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V", shift = At.Shift.BEFORE))
    private static void virtualAdditions$cancelMultishotLoad(ItemStack weaponStack, ItemStack projectileStack, LivingEntity shooter, CallbackInfoReturnable<List<ItemStack>> cir, @Local LocalIntRef i) {
        if (projectileStack.isIn(VAItemTags.CLIMBING_ROPES)) i.set(1);
    }
}

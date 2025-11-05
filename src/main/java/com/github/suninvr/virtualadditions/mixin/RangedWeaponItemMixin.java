package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public class RangedWeaponItemMixin {
    @Unique
    private static final Predicate<ItemStack> climbingRopePredicate = (itemStack -> itemStack.is(VAItemTags.CLIMBING_ROPES));

    @Inject(at = @At("HEAD"), method = "getHeldProjectile", cancellable = true)
    private static void virtualAdditions$getHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
        if(predicate.equals(ProjectileWeaponItem.ARROW_OR_FIREWORK)) {
            if (climbingRopePredicate.test(entity.getItemInHand(InteractionHand.OFF_HAND))) {
                cir.setReturnValue(entity.getItemInHand(InteractionHand.OFF_HAND));
            } else if (climbingRopePredicate.test(entity.getItemInHand(InteractionHand.MAIN_HAND))){
                cir.setReturnValue(entity.getItemInHand(InteractionHand.MAIN_HAND));
            }
        }
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V", shift = At.Shift.BEFORE))
    private static void virtualAdditions$cancelMultishotLoad(ItemStack weaponStack, ItemStack projectileStack, LivingEntity shooter, CallbackInfoReturnable<List<ItemStack>> cir, @Local LocalIntRef i) {
        if (projectileStack.is(VAItemTags.CLIMBING_ROPES)) i.set(1);
    }
}

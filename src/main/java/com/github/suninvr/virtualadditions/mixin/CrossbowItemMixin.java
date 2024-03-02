package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({"unused", "SameParameterValue"})
@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Inject(at = @At("HEAD"), method = "createArrowEntity", cancellable = true)
    void virtualAdditions$createClimbingRopeEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical, CallbackInfoReturnable<ProjectileEntity> cir) {
        if (projectileStack.isOf(VAItems.CLIMBING_ROPE)) {
            ClimbingRopeEntity projectileEntity = new ClimbingRopeEntity(shooter.getX(), shooter.getEyeY(), shooter.getZ(), world);
            projectileEntity.pickupType = shooter.isPlayer() && !shooter.isInCreativeMode() ? PersistentProjectileEntity.PickupPermission.ALLOWED : PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            projectileEntity.setShotFromCrossbow(true);
            cir.setReturnValue(projectileEntity);
        }
    }

    @Inject(method = "shoot", at = @At("HEAD"))
    void virtualAdditions$shootClimbingRope(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, LivingEntity target, CallbackInfo ci, @Local(argsOnly = true, ordinal = 0) LocalFloatRef speedRef, @Local(argsOnly = true, ordinal = 1) LocalFloatRef divRef){
        if (projectile instanceof ClimbingRopeEntity) {
            speedRef.set(speedRef.get() * 0.8F);
            divRef.set(0.0F);
        }
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({"unused", "SameParameterValue"})
@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Inject(at = @At("HEAD"), method = "createArrowEntity", cancellable = true)
    void virtualAdditions$createClimbingRopeEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical, CallbackInfoReturnable<ProjectileEntity> cir) {
        if (projectileStack.isIn(VAItemTags.CLIMBING_ROPES)) {
            ClimbingRopeEntity projectileEntity = new ClimbingRopeEntity(shooter.getX(), shooter.getEyeY(), shooter.getZ(), world, projectileStack, weaponStack);
            projectileEntity.pickupType = shooter.isPlayer() && !shooter.isInCreativeMode() ? PersistentProjectileEntity.PickupPermission.ALLOWED : PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            cir.setReturnValue(projectileEntity);
        }
    }
}

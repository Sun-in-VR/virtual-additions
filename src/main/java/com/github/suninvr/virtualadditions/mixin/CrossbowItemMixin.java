package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({"unused", "SameParameterValue"})
@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Inject(at = @At("HEAD"), method = "createProjectile", cancellable = true)
    void virtualAdditions$createClimbingRopeEntity(Level world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical, CallbackInfoReturnable<Projectile> cir) {
        if (projectileStack.is(VAItemTags.CLIMBING_ROPES)) {
            ClimbingRopeEntity projectileEntity = new ClimbingRopeEntity(shooter.getX(), shooter.getEyeY(), shooter.getZ(), world, projectileStack, weaponStack);
            projectileEntity.pickup = shooter.isAlwaysTicking() && !shooter.hasInfiniteMaterials() ? AbstractArrow.Pickup.ALLOWED : AbstractArrow.Pickup.CREATIVE_ONLY;
            projectileEntity.setOwner(shooter);
            cir.setReturnValue(projectileEntity);
        }
    }
}

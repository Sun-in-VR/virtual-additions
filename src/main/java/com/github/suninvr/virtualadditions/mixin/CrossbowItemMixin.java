package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    /**
     * Code used from {@link CrossbowItem}
     */
    @Inject(at = @At("HEAD"), method = "shoot", cancellable = true)
    private static void virtualAdditions$shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
        if (!world.isClient) {
            boolean bl = projectile.isOf(VAItems.CLIMBING_ROPE);
            if (bl) {
                //Code reused from CrossbowItem::shoot
                ProjectileEntity projectileEntity;
                projectileEntity = new ClimbingRopeEntity(shooter.getX(), shooter.getEyeY(), shooter.getZ(), world);
                projectileEntity.setOwner(shooter);

                if (shooter instanceof CrossbowUser crossbowUser) {
                    crossbowUser.shoot(crossbowUser.getTarget(), crossbow, projectileEntity, simulated);
                } else {
                    Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(simulated, vec3d.x, vec3d.y, vec3d.z);
                    Vec3d vec3d2 = shooter.getRotationVec(1.0F);
                    Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);
                    projectileEntity.setVelocity(vector3f.x(), vector3f.y(), vector3f.z(), speed * 0.8F, 0.0F);
                }

                crossbow.damage(2, shooter, (e) -> e.sendToolBreakStatus(hand));
                world.spawnEntity(projectileEntity);
                world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "loadProjectiles", cancellable = true)
    private static void virtualAdditions$loadProjectiles(LivingEntity shooter, ItemStack crossbow, CallbackInfoReturnable<Boolean> cir) {
        boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).getAbilities().creativeMode;
        ItemStack projectile = shooter.getProjectileType(crossbow);

        if (projectile.isOf(VAItems.CLIMBING_ROPE)) {
            cir.setReturnValue(virtualAdditions$loadProjectile(shooter, crossbow, projectile, false, bl));
        }
    }

    @Invoker("loadProjectile")
    private static boolean virtualAdditions$loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        throw new AssertionError();
    }
}

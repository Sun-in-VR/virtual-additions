package com.github.suninvr.virtualadditions.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.item.TomeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow protected boolean isSubmergedInWater;
    @Shadow public abstract boolean isSwimming();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    public void virtualAdditions$getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = ((PlayerEntity)(Object)this);
        Entity entity = player.getRootVehicle();
        boolean bl = entity.isOnGround();
        if (entity instanceof LivingEntity livingEntity) {
            bl = bl || livingEntity.isClimbing();
        }
        if (bl && !player.isOnGround()) cir.setReturnValue(cir.getReturnValue() * 5.0F);
    }


}

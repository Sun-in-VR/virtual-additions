package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSwimming();

    @Shadow @Final private PlayerInventory inventory;

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

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$damageArmorFromAcid(DamageSource source, float amount, CallbackInfo ci) {
        if (source.isIn(VADamageTypes.INCREASED_ARMOR_DAMAGE)) {
            if (amount > 1) amount *= 4;
            this.inventory.damageArmor(source, amount, PlayerInventory.ARMOR_SLOTS);
            ci.cancel();
        }
//
    }


}

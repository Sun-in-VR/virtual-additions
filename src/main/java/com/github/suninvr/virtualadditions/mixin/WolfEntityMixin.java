package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends LivingEntity {
    protected WolfEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;getBodyArmor()Lnet/minecraft/item/ItemStack;", ordinal = 0, shift = At.Shift.BEFORE))
    void virtualAdditions$damageArmorFromAcid(ServerWorld world, DamageSource source, float amount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef amountRef) {
        if (source.isIn(VADamageTypes.INCREASED_ARMOR_DAMAGE)) amountRef.set(amountRef.get() * 3.0F);
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(method = "damageArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V", shift = At.Shift.BEFORE))
    private void virtualAdditions$increaseArmorDamage(DamageSource damageSource, float amount, int[] slots, CallbackInfo ci) {
        if (damageSource.isOf(VADamageTypes.ACID) || damageSource.isOf(VADamageTypes.ACID_SPIT)) amount *= 4.0F;
    }
}

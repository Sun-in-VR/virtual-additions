package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin extends Item {
    ArrayList<StatusEffectInstance> persistentEffects = new ArrayList<>();
    public MilkBucketItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.BEFORE))
    void virtualAdditions$getPersistentStatusEffects(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        user.getStatusEffects().forEach((statusEffectInstance -> {
            if (statusEffectInstance.getEffectType().equals(VAStatusEffects.IOLITE_INTERFERENCE)) persistentEffects.add(statusEffectInstance);
        }));
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.AFTER))
    void virtualAdditions$reapplyPersistentStatusEffects(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        persistentEffects.forEach(user::addStatusEffect);
        persistentEffects.clear();
    }
}

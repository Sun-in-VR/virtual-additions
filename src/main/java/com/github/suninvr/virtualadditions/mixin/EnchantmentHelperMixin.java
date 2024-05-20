package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getBlockExperience", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableFloat;intValue()I", shift = At.Shift.BEFORE))
    private static void virtualAdditions$getBlockExperienceForGildedTool(ServerWorld world, ItemStack stack, int baseBlockExperience, CallbackInfoReturnable<Integer> cir, @Local LocalRef<MutableFloat> mutableFloat) {
        float f = mutableFloat.get().floatValue();
        if (GildedToolUtil.getGildType(stack).equals(GildTypes.EMERALD)) mutableFloat.set(new MutableFloat(f * 1.6F));
    }
}

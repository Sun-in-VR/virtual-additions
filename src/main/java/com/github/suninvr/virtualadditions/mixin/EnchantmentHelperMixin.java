package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "processBlockExperience", at = @At(value = "RETURN"), cancellable = true)
    private static void virtualAdditions$getBlockExperienceForGildedTool(ServerLevel world, ItemStack stack, int baseBlockExperience, CallbackInfoReturnable<Integer> cir, @Local MutableFloat mutableFloat) {
        GildType type = stack.get(VADataComponentTypes.GILD_TYPE);
        if (!VAGildTypes.EMERALD.equals(type)) return;
        float f = mutableFloat.floatValue();
        cir.setReturnValue((int) Math.ceil(new MutableFloat(f * 1.6F).floatValue()));
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.llamalad7.mixinextras.sugar.Local;
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
    @Inject(method = "getBlockExperience", at = @At(value = "RETURN"), cancellable = true)
    private static void virtualAdditions$getBlockExperienceForGildedTool(ServerWorld world, ItemStack stack, int baseBlockExperience, CallbackInfoReturnable<Integer> cir, @Local MutableFloat mutableFloat) {
        GildTypeComponent component = stack.get(VADataComponentTypes.GILD_TYPE_COMPONENT);
        if (component == null || !component.type().value().equals(VAGildTypes.EMERALD)) return;
        float f = mutableFloat.floatValue();
        cir.setReturnValue((int) Math.ceil(new MutableFloat(f * 1.6F).floatValue()));
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {
    @Inject(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/featuretoggle/FeatureManager$Builder;build()Lnet/minecraft/resource/featuretoggle/FeatureManager;", shift = At.Shift.BEFORE))
    private static void virtualAdditions$initPreviewFeatureFlag(CallbackInfo ci, @Local LocalRef<FeatureManager.Builder> builderRef) {
        VirtualAdditions.PREVIEW = builderRef.get().addFlag(VirtualAdditions.idOf("preview"));
    }
}

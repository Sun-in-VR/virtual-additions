package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/resource/featuretoggle/FeatureFlags;UPDATE_1_20:Lnet/minecraft/resource/featuretoggle/FeatureFlag;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void virtualAdditions$addPreviewFeatureFlag(CallbackInfo ci, FeatureManager.Builder builder) {
        VirtualAdditions.PREVIEW = builder.addFlag(VirtualAdditions.idOf("preview"));
    }
}

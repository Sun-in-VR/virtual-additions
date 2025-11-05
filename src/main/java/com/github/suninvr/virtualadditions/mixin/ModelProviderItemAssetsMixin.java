package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.datagen.VAModelProvider;
import net.minecraft.client.data.models.ModelProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelProvider.ItemInfoCollector.class)
public class ModelProviderItemAssetsMixin {
    @Inject(method = "finalizeAndValidate", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$cancelIfHalted(CallbackInfo ci) {
        if (VAModelProvider.isModelGenerationHalted()) {
            VAModelProvider.unhaltModelGeneration();
            ci.cancel();
        }
    }
}

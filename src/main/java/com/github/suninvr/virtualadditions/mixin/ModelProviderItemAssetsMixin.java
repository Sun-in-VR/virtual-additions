package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.datagen.VAModelProvider;
import net.minecraft.client.data.ModelProvider;
import net.minecraft.client.item.ItemAsset;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelProvider.ItemAssets.class)
public class ModelProviderItemAssetsMixin {
    @Shadow @Final private Map<Item, ItemAsset> ITEM_ASSETS;

    @Inject(method = "accept(Lnet/minecraft/item/Item;Lnet/minecraft/client/item/ItemAsset;)V", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$acceptConditionally(Item item, ItemAsset asset, CallbackInfo ci) {
        if (this.ITEM_ASSETS.get(item) != null) ci.cancel();
    }

    @Inject(method = "resolveAndValidate", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$resolveAndValidateOnce(CallbackInfo ci) {
        if (VAModelProvider.haltAutomaticGeneration()) {
            VAModelProvider.setCanAutomaticallyGenerate();
            ci.cancel();
        }
    }
}

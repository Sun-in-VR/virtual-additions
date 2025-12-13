package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.item.gild.GildedItemComponentHandler;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;

@Mixin(PatchedDataComponentMap.class)
public class PatchedDataComponentMapMixin {
    @Inject(method = "fromPatch", at = @At("HEAD"))
    private static void virtualAdditions$fromPatchWithGild(DataComponentMap dataComponentMap, DataComponentPatch dataComponentPatch, CallbackInfoReturnable<PatchedDataComponentMap> cir, @Local(argsOnly = true) LocalRef<DataComponentMap> mapLocalRef) {
        Optional<? extends GildType> typeHolder = dataComponentPatch.get(VADataComponentTypes.GILD_TYPE);
        if (Objects.nonNull(typeHolder)) typeHolder.ifPresent(type -> mapLocalRef.set(GildedItemComponentHandler.getOrCompute(dataComponentMap, type)));
    }

}

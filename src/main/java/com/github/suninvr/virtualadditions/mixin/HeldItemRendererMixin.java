package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingSpyglass()Z"))
    boolean virtualAdditions$isUsingSpectralSpyglass(AbstractClientPlayerEntity instance, Operation<Boolean> original) {
        return original.call(instance) || ProjectionSpyglassItem.isInUseBy(instance);
    }
}

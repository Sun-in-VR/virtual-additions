package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingSpyglass()Z"))
    boolean virtualAdditions$isUsingSpectralSpyglass(AbstractClientPlayerEntity instance) {
        return ProjectionSpyglassItem.isInUseBy(instance) || instance.isUsingSpyglass();
    }
}

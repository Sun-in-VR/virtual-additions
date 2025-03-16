package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "createColorId", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$createVirtualAdditionsColorId(DyeColor color, CallbackInfoReturnable<Identifier> cir) {
        if (VADyeColors.isFromVirtualAdditions(color)) cir.setReturnValue(
                idOf(color.getId())
        );
    }
    @Inject(method = "createShulkerId", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$createVirtualAdditionsShulkerId(DyeColor color, CallbackInfoReturnable<Identifier> cir) {
        if (VADyeColors.isFromVirtualAdditions(color)) cir.setReturnValue(
                idOf("shulker_" + color.getId())
        );
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(Sheets.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "colorToResourceMaterial", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$createVirtualAdditionsColorId(DyeColor color, CallbackInfoReturnable<Identifier> cir) {
        if (VADyeColors.isFromVirtualAdditions(color)) cir.setReturnValue(
                idOf(color.getName())
        );
    }
    @Inject(method = "colorToShulkerMaterial", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$createVirtualAdditionsShulkerId(DyeColor color, CallbackInfoReturnable<Identifier> cir) {
        if (VADyeColors.isFromVirtualAdditions(color)) cir.setReturnValue(
                idOf("shulker_" + color.getName())
        );
    }
}

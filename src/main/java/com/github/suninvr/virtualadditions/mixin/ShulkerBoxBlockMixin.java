package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
    @Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
    private static void virtualAdditions$getCustomShulkerBox(DyeColor dyeColor, CallbackInfoReturnable<Block> cir) {
        if (dyeColor != null) {
            if (dyeColor.equals(VADyeColors.CHARTREUSE)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.MAROON)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.INDIGO)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.PLUM)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.VIRIDIAN)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.TAN)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.SINOPIA)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.LILAC)) cir.setReturnValue(Blocks.WHITE_SHULKER_BOX);
        }
    }
}

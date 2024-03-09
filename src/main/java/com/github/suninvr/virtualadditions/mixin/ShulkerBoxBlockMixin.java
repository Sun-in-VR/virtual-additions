package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VAItems;
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
            boolean bl = VirtualAdditions.areBlocksInitialized();
            if (dyeColor.equals(VADyeColors.CHARTREUSE)) cir.setReturnValue(bl ? VABlocks.CHARTREUSE_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.MAROON)) cir.setReturnValue(bl ? VABlocks.MAROON_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.INDIGO)) cir.setReturnValue(bl ? VABlocks.INDIGO_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.PLUM)) cir.setReturnValue(bl ? VABlocks.PLUM_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.VIRIDIAN)) cir.setReturnValue(bl ? VABlocks.VIRIDIAN_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.TAN)) cir.setReturnValue(bl ? VABlocks.TAN_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.SINOPIA)) cir.setReturnValue(bl ? VABlocks.SINOPIA_SHULKER_BOX : Blocks.SHULKER_BOX);
            if (dyeColor.equals(VADyeColors.LILAC)) cir.setReturnValue(bl ? VABlocks.LILAC_SHULKER_BOX : Blocks.SHULKER_BOX);
        }
    }
}

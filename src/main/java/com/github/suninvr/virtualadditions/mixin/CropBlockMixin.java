package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Inject(method = "getGrowthSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private static void virtualAdditions$getGrowthSpeedOnPlanter(Block block, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Float> cir) {
        BlockState state = blockGetter.getBlockState(blockPos.below());
        if (state.is(VABlockTags.PLANTERS)) cir.setReturnValue(5.0F);
    }

    @ModifyExpressionValue(method = "mayPlaceOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    boolean virtualAdditions$mayPlaceOnPlanter(boolean original, @Local(argsOnly = true) BlockState state) {
        return original || state.is(VABlockTags.PLANTERS);
    }
}

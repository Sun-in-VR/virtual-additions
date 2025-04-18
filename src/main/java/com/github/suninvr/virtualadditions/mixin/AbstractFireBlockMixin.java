package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
    @Inject(method = "getState", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getSpectralFireState(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(VABlocks.SPECTRAL_SAND)) cir.setReturnValue(VABlocks.SPECTRAL_FIRE.getDefaultState());

    }
}

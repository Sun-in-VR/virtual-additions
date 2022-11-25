package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WallBlock.class)
public class WallBlockMixin {
    @Inject(method = "shouldConnectTo", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$shouldConnectToHedges(BlockState state, boolean faceFullSquare, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (!(cir.getReturnValue())) {
            if (state.isIn(VABlockTags.HEDGES)) cir.setReturnValue(true);
        }
    }
}

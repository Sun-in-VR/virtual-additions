package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$connectsToRedstoneBridge(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.isOf(VABlocks.REDSTONE_BRIDGE)) {
            Direction direction = state.get(RedstoneBridgeBlock.FACING);
            cir.setReturnValue(direction == dir || direction.getOpposite() == dir);
        }
    }
}

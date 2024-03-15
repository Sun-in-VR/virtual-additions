package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract void updateComparators(BlockPos pos, Block block);

    @Shadow public abstract BlockState getBlockState(BlockPos pos);

    @Unique static int redstoneBridgeUpdateLength = 0;

    @Inject(method = "updateComparators", at = @At(value = "TAIL"))
    void virtualAdditions$updateRedstoneBridgeComparatorOutputVertical(BlockPos pos, Block block, CallbackInfo ci) {
        for (Direction direction : Direction.Type.VERTICAL) {
            virtualAdditions$tryUpdateRedstoneBridge(pos, direction);
        }
    }

    @Inject(method = "updateComparators", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isSolidBlock(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.BEFORE))
    void virtualAdditions$updateRedstoneBridgeComparatorOutputHorizontal(BlockPos pos, Block block, CallbackInfo ci, @Local Direction direction) {
        virtualAdditions$tryUpdateRedstoneBridge(pos, direction);
    }

    @Unique
    void virtualAdditions$tryUpdateRedstoneBridge(BlockPos pos, Direction dir) {
        BlockPos blockPos = pos.offset(dir);
        BlockState state = this.getBlockState(blockPos);
        if (state.isOf(VABlocks.REDSTONE_BRIDGE) && state.get(RedstoneBridgeBlock.FACING).equals(dir)) {
            BlockState blockState = this.getBlockState(pos);
            if (blockState.isOf(VABlocks.REDSTONE_BRIDGE) && !blockState.get(RedstoneBridgeBlock.FACING).equals(dir)) return;
            updateComparators(blockPos, VABlocks.REDSTONE_BRIDGE);
        }
    }
}

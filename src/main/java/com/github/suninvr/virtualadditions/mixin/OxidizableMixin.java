package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.OxidizableClimbingRopeAnchor;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Oxidizable.class)
public interface OxidizableMixin {
    @Inject(method = "getDecreasedOxidationBlock", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getDecreasedOxidationClimbingRope(Block block, CallbackInfoReturnable<Optional<Block>> cir) {
        if (block instanceof OxidizableClimbingRopeAnchor) {
            cir.setReturnValue(Optional.ofNullable(VACollections.CLIMBING_ROPE_OXIDIZATION_DECREASES.get().get(block)));
        }
    }

    @Inject(method = "getIncreasedOxidationBlock", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getIncreasedOxidationClimbingRope(Block block, CallbackInfoReturnable<Optional<Block>> cir) {
        if (block instanceof OxidizableClimbingRopeAnchor) {
            cir.setReturnValue(Optional.ofNullable(VACollections.CLIMBING_ROPE_OXIDIZATION_INCREASES.get().get(block)));
        }
    }

    @Inject(method = "getUnaffectedOxidationBlock", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getUnaffectedOxidationClimbingRope(Block block, CallbackInfoReturnable<Optional<Block>> cir) {
        if (block instanceof OxidizableClimbingRopeAnchor) {
            cir.setReturnValue(Optional.ofNullable(VABlocks.CLIMBING_ROPE_ANCHOR));
        }
    }

}

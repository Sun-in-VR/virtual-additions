package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.google.common.collect.BiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
    @Inject(method = "getWaxedState", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getWaxedClimbingRopeState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (state.getBlock() instanceof ClimbingRopeAnchorBlock) cir.setReturnValue( Optional.ofNullable((Block)((BiMap) VACollections.UNWAXED_TO_WAXED_CLIMBING_ROPES.get()).get(state.getBlock())).map((block) -> block.getStateWithProperties(state)));
    }
}

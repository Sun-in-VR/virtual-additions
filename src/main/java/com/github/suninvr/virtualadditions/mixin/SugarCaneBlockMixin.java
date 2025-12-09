package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    @ModifyExpressionValue(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 1))
    boolean virtualAdditions$canSurviveOnPlanter(boolean original, @Local(ordinal = 1) BlockState state) {
        return original || state.is(VABlockTags.PLANTERS);
    }
}

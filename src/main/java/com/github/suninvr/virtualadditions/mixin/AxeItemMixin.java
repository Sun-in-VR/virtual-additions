package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VACollections;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z", ordinal = 2, shift = At.Shift.BEFORE))
    void virtualAdditions$isClimbingRope(Level world, BlockPos pos, Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir, @Local(ordinal = 2)LocalRef<Optional<BlockState>> optionalState) {
        if (optionalState.get().isEmpty()) {
            optionalState.set(Optional.ofNullable(VACollections.WAXED_TO_UNWAXED_CLIMBING_ROPES.get().get(state.getBlock())).map((block) -> block.withPropertiesOf(state)));
        }
    }
}

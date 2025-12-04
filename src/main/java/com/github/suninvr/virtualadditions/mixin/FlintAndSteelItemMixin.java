package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.IncenseBlock;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {

    @ModifyExpressionValue(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CandleBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    boolean virtualAdditions$useOnIncense(boolean original, @Local BlockState state) {
        return original || IncenseBlock.canLight(state);
    }

}

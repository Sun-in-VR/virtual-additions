package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAFluids;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterFluid.class)
public class WaterFluidMixin {
    @Inject(method = "isSame", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$waterMatchesAcid(Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (fluid == VAFluids.ACID || fluid == VAFluids.FLOWING_ACID) cir.setReturnValue(true);
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.LivingEntityInterface;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "getXpToDrop", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getModifiedXpToDrop(CallbackInfoReturnable<Integer> cir) {
        float mul = ((LivingEntityInterface)this).virtualAdditions$getXpModifier();
        if (mul > 1) {
            cir.setReturnValue((int) Math.ceil(cir.getReturnValueI() * mul));
        }
    }
}

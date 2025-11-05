package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.world.TickRateManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TickRateManager.class)
public class TickManagerMixin {
    //@WrapOperation(method = "shouldSkipTick", at = @At(value = "CONSTANT", args = "classValue=net/minecraft/entity/player/PlayerEntity", opcode = Opcodes.INSTANCEOF))
    //boolean virtualAdditions$shouldSkipTickForPlayerProjectionEntity(Object object, Operation<Boolean> original) {
    //    return original.call(object) || object instanceof PlayerProjectionEntity;
    //}
}

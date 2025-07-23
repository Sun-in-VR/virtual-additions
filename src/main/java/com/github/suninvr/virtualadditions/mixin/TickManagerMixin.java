package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.tick.TickManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TickManager.class)
public class TickManagerMixin {
    @WrapOperation(method = "shouldSkipTick", at = @At(value = "CONSTANT", args = "classValue=net/minecraft/entity/player/PlayerEntity", opcode = Opcodes.INSTANCEOF))
    boolean virtualAdditions$shouldSkipTickForPlayerProjectionEntity(Object object, Operation<Boolean> original) {
        return original.call(object) || object instanceof PlayerProjectionEntity;
    }
}

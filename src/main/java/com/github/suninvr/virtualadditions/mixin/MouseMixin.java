package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public class MouseMixin {
    @WrapOperation(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    void virtualAdditions$changePlayerProjectionLookDirection(ClientPlayerEntity instance, double d, double e, Operation<Void> original) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerProjectionEntity entity && entity.getPlayer().isMainPlayer()) {
            entity.changeLookDirection(d, e);
            entity.lastYaw = entity.bodyYaw = entity.headYaw = entity.getYaw();
            entity.lookDirectionChanged = true;
        } else {
            original.call(instance, d, e);
        }
    }
}

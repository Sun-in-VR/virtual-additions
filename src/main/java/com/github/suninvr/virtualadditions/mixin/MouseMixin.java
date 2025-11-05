package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @WrapOperation(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    void virtualAdditions$changePlayerProjectionLookDirection(LocalPlayer instance, double d, double e, Operation<Void> original) {
        if (Minecraft.getInstance().getCameraEntity() instanceof PlayerProjectionEntity entity && entity.getPlayer().isLocalPlayer()) {
            entity.turn(d, e);
            entity.yRotO = entity.yBodyRot = entity.yHeadRot = entity.getYRot();
            entity.lookDirectionChanged = true;
        } else {
            original.call(instance, d, e);
        }
    }
}

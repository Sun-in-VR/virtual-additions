package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.class_11658;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow private @Nullable ClientWorld world;

    @ModifyExpressionValue(method = "method_72917", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/Frustum;DDD)Z"))
    boolean virtualAdditions$shouldRenderClientPlayer(boolean original, @Local(argsOnly = true) Camera camera, @Local Entity entity) {
        return original || (camera.getFocusedEntity() instanceof PlayerProjectionEntity && MinecraftClient.getInstance().player != null && entity.getUuid().equals(MinecraftClient.getInstance().player.getUuid()));
    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$cancelRenderSkyWhenProjectionIsPhasing(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
        if (camera.getFocusedEntity() instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall()) ci.cancel();
    }

}

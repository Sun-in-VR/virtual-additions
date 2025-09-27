package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Definition(id = "ClientPlayerEntity", type = ClientPlayerEntity.class)
    @Expression("? instanceof ClientPlayerEntity")
    @ModifyExpressionValue(method = "fillEntityRenderStates", at = @At("MIXINEXTRAS:EXPRESSION"))
    boolean virtualAdditions$forceRenderClientPlayer(boolean original, @Local Entity entity) {
        return original && !ProjectionSpyglassItem.isInUseBy(entity);
    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$cancelRenderSkyWhenProjectionIsPhasing(FrameGraphBuilder frameGraphBuilder, Camera camera, GpuBufferSlice fogBuffer, CallbackInfo ci) {
        if (camera.getFocusedEntity() instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall()) ci.cancel();
    }

}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow private @Nullable ClientWorld world;

    @Inject(method = "getEntitiesToRender", at = @At("RETURN"))
    void virtualAdditions$getEntitiesToRenderWhileUsingSpectralSpyglass(Camera camera, Frustum frustum, List<Entity> output, CallbackInfoReturnable<Boolean> cir) {
        if (camera.getFocusedEntity() instanceof PlayerProjectionEntity && MinecraftClient.getInstance().player != null) output.add(MinecraftClient.getInstance().player);

    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$cancelRenderSkyWhenProjectionIsPhasing(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
        if (camera.getFocusedEntity() instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall()) ci.cancel();
    }

}

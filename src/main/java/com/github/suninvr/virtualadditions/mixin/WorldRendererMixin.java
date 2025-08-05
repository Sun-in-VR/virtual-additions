package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @ModifyExpressionValue(method = "fillEntityRenderStates", at = @At(value = "CONSTANT", args = "classValue=net/minecraft/client/network/ClientPlayerEntity", opcode = Opcodes.INSTANCEOF))
    boolean virtualAdditions$forceRenderClientPlayer(boolean original, @Local(argsOnly = true) Camera camera, @Local Entity entity) {
        return original && !ProjectionSpyglassItem.isInUseBy(entity);
    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$cancelRenderSkyWhenProjectionIsPhasing(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
        if (camera.getFocusedEntity() instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall()) ci.cancel();
    }

}

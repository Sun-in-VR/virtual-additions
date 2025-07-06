package com.github.suninvr.virtualadditions.client.render.fog;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.fog.FogModifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PlayerProjectionPhasingFogModifier extends FogModifier {

    @Override
    public void applyStartEndModifier(FogData data, Entity cameraEntity, BlockPos cameraPos, ClientWorld world, float viewDistance, RenderTickCounter tickCounter) {
        data.environmentalStart = 1.0F;
        data.environmentalEnd = 5.0F;
        data.skyEnd = 1.6F;
        data.cloudEnd = 1.6F;
    }

    @Override
    public boolean isDarknessModifier() {
        return true;
    }

    @Override
    public int getFogColor(ClientWorld world, Camera camera, int viewDistance, float skyDarkness) {
        return 0x646473;
    }

    @Override
    public boolean shouldApply(@Nullable CameraSubmersionType submersionType, Entity cameraEntity) {
        return cameraEntity instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall();
    }
}

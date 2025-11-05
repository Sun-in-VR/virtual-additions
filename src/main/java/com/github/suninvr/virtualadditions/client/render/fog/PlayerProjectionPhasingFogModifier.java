package com.github.suninvr.virtualadditions.client.render.fog;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PlayerProjectionPhasingFogModifier extends FogEnvironment {

    @Override
    public boolean modifiesDarkness() {
        return true;
    }

    @Override
    public void setupFog(FogData data, Camera camera, ClientLevel clientWorld, float f, DeltaTracker renderTickCounter) {
        data.environmentalStart = 1.0F;
        data.environmentalEnd = 5.0F;
        data.skyEnd = 1.6F;
        data.cloudEnd = 1.6F;
    }

    @Override
    public int getBaseColor(ClientLevel world, Camera camera, int viewDistance, float skyDarkness) {
        return 0x2e404c;
    }

    @Override
    public boolean isApplicable(@Nullable FogType submersionType, Entity cameraEntity) {
        return cameraEntity instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.isPhasingThroughWall();
    }
}

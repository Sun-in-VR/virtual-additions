package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import org.joml.Vector3fc;

public class ColorfulPowerParticle extends TrialSpawnerDetectionParticle {
    public ColorfulPowerParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scale, Vector3fc color, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, scale, spriteProvider);
        this.setColor(color.x(), color.y(), color.z());
    }
}

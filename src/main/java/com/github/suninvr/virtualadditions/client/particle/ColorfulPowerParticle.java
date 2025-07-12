package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector3f;

public class ColorfulPowerParticle extends TrialSpawnerDetectionParticle {
    public ColorfulPowerParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scale, Vector3f color, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, scale, spriteProvider);
        this.setColor(color.x, color.y, color.z);
    }
}

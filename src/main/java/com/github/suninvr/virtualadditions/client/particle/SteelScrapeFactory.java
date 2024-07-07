package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SteelScrapeFactory implements ParticleFactory<SimpleParticleType> {
    private final double velocityMultiplier = 0.01;
    private final SpriteProvider spriteProvider;

    public SteelScrapeFactory(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        GlowParticle glowParticle = new GlowParticle(world, x, y, z, 0.0, 0.0, 0.0, this.spriteProvider);
        if (world.random.nextBoolean()) {
            glowParticle.setColor(0.47f, 0.2f, 0.24f);
        } else {
            glowParticle.setColor(0.64f, 0.36f, 0.35f);
        }
        glowParticle.setVelocity(velocityX * 0.01, velocityY * 0.01, velocityZ * 0.01);
        int j = 10;
        int k = 40;
        glowParticle.setMaxAge(world.random.nextInt(30) + 10);
        return glowParticle;
    }
}

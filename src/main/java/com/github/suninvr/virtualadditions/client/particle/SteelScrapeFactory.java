package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class SteelScrapeFactory implements ParticleProvider<SimpleParticleType> {
    private final double velocityMultiplier = 0.01;
    private final SpriteSet spriteProvider;

    public SteelScrapeFactory(SpriteSet spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
        GlowParticle glowParticle = new GlowParticle(world, x, y, z, 0.0, 0.0, 0.0, this.spriteProvider);
        if (world.random.nextBoolean()) {
            glowParticle.setColor(0.47f, 0.2f, 0.24f);
        } else {
            glowParticle.setColor(0.64f, 0.36f, 0.35f);
        }
        glowParticle.setParticleSpeed(velocityX * 0.01, velocityY * 0.01, velocityZ * 0.01);
        glowParticle.setLifetime(world.random.nextInt(30) + 10);
        return glowParticle;
    }
}

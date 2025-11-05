package com.github.suninvr.virtualadditions.client.particle;

import com.github.suninvr.virtualadditions.particle.ColorfulPowerParticleEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class PowerParticleFactory implements ParticleProvider<SimpleParticleType> {
    SpriteSet spriteProvider;

    public PowerParticleFactory(SpriteSet spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
        return new TrialSpawnerDetectionParticle(world, x, y, z, 0, 0, 0, 1.5f, this.spriteProvider);
    }

    public static class Color implements ParticleProvider<ColorfulPowerParticleEffect> {
        SpriteSet spriteProvider;

        public Color(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(ColorfulPowerParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new ColorfulPowerParticle(world, x, y, z, 0, 0, 0, 1.5f, parameters.getColor(), this.spriteProvider);
        }
    }
}

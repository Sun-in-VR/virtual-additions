package com.github.suninvr.virtualadditions.client.particle;

import com.github.suninvr.virtualadditions.particle.ColorfulPowerParticleEffect;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class PowerParticleFactory implements ParticleFactory<SimpleParticleType> {
    SpriteProvider spriteProvider;

    public PowerParticleFactory(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return new TrialSpawnerDetectionParticle(clientWorld, d, e, f, 0, 0, 0, 1.5f, this.spriteProvider);
    }

    public static class Color implements ParticleFactory<ColorfulPowerParticleEffect> {
        SpriteProvider spriteProvider;

        public Color(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(ColorfulPowerParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ColorfulPowerParticle(world, x, y, z, 0, 0, 0, 1.5f, parameters.getColor(), this.spriteProvider);
        }
    }
}

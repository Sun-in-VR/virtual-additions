package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class InterferenceFactory extends TrialSpawnerDetectionParticle.Factory {
    SpriteProvider spriteProvider;

    public InterferenceFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
        this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return new TrialSpawnerDetectionParticle(clientWorld, d, e, f, 0, 0, 0, 1.5f, this.spriteProvider);
    }
}

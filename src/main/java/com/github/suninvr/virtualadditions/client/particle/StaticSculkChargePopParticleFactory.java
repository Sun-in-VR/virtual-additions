package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SculkChargePopParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class StaticSculkChargePopParticleFactory implements ParticleProvider<SimpleParticleType> {

    SpriteSet spriteProvider;

    public StaticSculkChargePopParticleFactory(SpriteSet spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
        SculkChargePopParticle sculkChargePopParticle = new SculkChargePopParticle(world, x, y, z, 0, 0, 0, this.spriteProvider);
        sculkChargePopParticle.setAlpha(1.0F);
        sculkChargePopParticle.setLifetime(random.nextInt(4) + 6);
        return sculkChargePopParticle;
    }
}

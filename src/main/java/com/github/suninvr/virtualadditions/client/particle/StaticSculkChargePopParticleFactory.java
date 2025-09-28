package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SculkChargePopParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class StaticSculkChargePopParticleFactory implements ParticleFactory<SimpleParticleType> {

    SpriteProvider spriteProvider;

    public StaticSculkChargePopParticleFactory(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
        SculkChargePopParticle sculkChargePopParticle = new SculkChargePopParticle(world, x, y, z, 0, 0, 0, this.spriteProvider);
        sculkChargePopParticle.setAlpha(1.0F);
        sculkChargePopParticle.setMaxAge(random.nextInt(4) + 6);
        return sculkChargePopParticle;
    }
}

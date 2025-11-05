package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SpringLotusPollenParticle extends SingleQuadParticle {
    protected SpringLotusPollenParticle(ClientLevel clientWorld, SpriteSet spriteProvider, double d, double e, double f, double x, double y, double z) {
        super(clientWorld, d, e, f, x, y, z, spriteProvider.first());
        this.setSize(0.01F, 0.01F);
        this.gravity = 1.0F;
        this.quadSize *= this.random.nextFloat() * 0.1F + 0.8F;
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = true;
        this.friction = 0.95F;
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            SpringLotusPollenParticle particle = new SpringLotusPollenParticle(world, this.spriteProvider, x, y, z, 0.0, 0.0, 0.0);
            particle.lifetime = Mth.randomBetweenInclusive(world.random, 20, 100);
            particle.alpha = 50;
            if (world.random.nextBoolean()) particle.setColor(0.93F, 0.87F, 0.27F);
            else particle.setColor(0.78F, 0.44F, 0.53F);
            particle.setParticleSpeed(velocityX * 0.25, velocityY + (world.random.nextDouble() * 2.0), velocityZ * 0.25);
            return particle;
        }
    }
}

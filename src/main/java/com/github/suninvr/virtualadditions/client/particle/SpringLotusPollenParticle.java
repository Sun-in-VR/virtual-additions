package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SpringLotusPollenParticle extends SpriteBillboardParticle {
    protected SpringLotusPollenParticle(ClientWorld clientWorld, SpriteProvider spriteProvider, double d, double e, double f, double x, double y, double z) {
        super(clientWorld, d, e, f, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.gravityStrength = 1.0F;
        this.scale *= this.random.nextFloat() * 0.1F + 0.8F;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 0.95F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SpringLotusPollenParticle particle = new SpringLotusPollenParticle(world, this.spriteProvider, x, y, z, 0.0, 0.0, 0.0);
            particle.maxAge = MathHelper.nextBetween(world.random, 20, 100);
            particle.alpha = 50;
            if (world.random.nextBoolean()) particle.setColor(0.93F, 0.87F, 0.27F);
            else particle.setColor(0.96F, 0.58F, 0.39F);
            particle.setVelocity(velocityX * 0.25, velocityY + (world.random.nextDouble() * 2.0), velocityZ * 0.25);
            return particle;
        }
    }
}

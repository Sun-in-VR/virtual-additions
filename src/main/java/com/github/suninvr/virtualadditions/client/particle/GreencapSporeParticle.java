package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GreencapSporeParticle extends SpriteBillboardParticle {
    protected GreencapSporeParticle(ClientWorld clientWorld, SpriteProvider spriteProvider, double d, double e, double f, double x, double y, double z) {
        super(clientWorld, d, e, f, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.8F;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.collidesWithWorld = false;
        this.velocityMultiplier = 0.97F;
        this.gravityStrength = 0.0F;
    }

    @Override
    protected int getBrightness(float tint) {
        return 120;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            GreencapSporeParticle particle = new GreencapSporeParticle(world, this.spriteProvider, x, y, z, 0.0, 0.0, 0.0) {
                public Optional<ParticleGroup> getGroup() {
                    return Optional.of(ParticleGroup.SPORE_BLOSSOM_AIR);
                }
            };
            particle.maxAge = MathHelper.nextBetween(world.random, 100, 200);
            particle.gravityStrength = -0.01F;
            particle.alpha = 50;
            particle.setColor(0.0F, 0.992F, 0.564F);
            return particle;
        }
    }
}

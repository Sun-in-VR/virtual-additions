package com.github.suninvr.virtualadditions.client.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class AcidSplashEmitterParticle extends NoRenderParticle {
    private int age;
    protected AcidSplashEmitterParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    public void tick() {
        for(int i = 0; i < 2; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            this.world.addParticleClient(VAParticleTypes.ACID_SPLASH, d, this.y, f, 0.0, 0.0, 0.0);
        }

        ++this.age;
        if (this.age >= 8) {
            this.markDead();
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new AcidSplashEmitterParticle(world, x, y, z);
        }
    }
}

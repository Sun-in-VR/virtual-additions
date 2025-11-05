package com.github.suninvr.virtualadditions.client.particle;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class AcidSplashEmitterParticle extends NoRenderParticle {
    private int age;
    protected AcidSplashEmitterParticle(ClientLevel clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    public void tick() {
        for(int i = 0; i < 2; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            this.level.addParticle(VAParticleTypes.ACID_SPLASH, d, this.y, f, 0.0, 0.0, 0.0);
        }

        ++this.age;
        if (this.age >= 8) {
            this.remove();
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new AcidSplashEmitterParticle(world, x, y, z);
        }
    }
}

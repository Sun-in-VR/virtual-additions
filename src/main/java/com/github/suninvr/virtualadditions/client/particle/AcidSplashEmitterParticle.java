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

public class AcidSplashEmitterParticle extends NoRenderParticle {
    private int age;
    protected AcidSplashEmitterParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    public void tick() {
        for(int i = 0; i < 2; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            this.world.addParticle(VAParticleTypes.ACID_SPLASH, d, this.y, f, 0.0, 0.0, 0.0);
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

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new AcidSplashEmitterParticle(clientWorld, d, e, f);
        }
    }
}

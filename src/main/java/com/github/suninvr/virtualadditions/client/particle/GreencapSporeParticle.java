package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleLimit;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class GreencapSporeParticle extends SingleQuadParticle {

    protected GreencapSporeParticle(ClientLevel clientWorld, SpriteSet spriteProvider, double d, double e, double f, double x, double y, double z) {
        super(clientWorld, d, e, f, x, y, z, spriteProvider.first());
        this.setSize(0.01F, 0.01F);
        this.quadSize *= this.random.nextFloat() * 0.1F + 0.8F;
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.friction = 0.997F;
        this.gravity = 0.0F;
    }

    @Override
    protected int getLightColor(float tint) {
        int skyLight = 0;
        int blockLight = 0;
        BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
        if (this.level.hasChunkAt(blockPos)) {
            BlockState state = level.getBlockState(blockPos);
            if (state.emissiveRendering(this.level, blockPos)) return 15728880;
            skyLight = this.level.getBrightness(LightLayer.SKY, blockPos);
            blockLight = this.level.getBrightness(LightLayer.BLOCK, blockPos);
            int luminance = state.getLightEmission();
            if (blockLight < luminance) blockLight = luminance;

            skyLight = Math.max(2, skyLight) << 20;
            blockLight = Math.max(2, blockLight) << 4;
        }
        return skyLight | blockLight;
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
            GreencapSporeParticle particle = new GreencapSporeParticle(world, this.spriteProvider, x, y, z, 0.0, 0.0, 0.0) {
                public Optional<ParticleLimit> getParticleLimit() {
                    return Optional.of(ParticleLimit.SPORE_BLOSSOM);
                }
            };
            particle.lifetime = Mth.randomBetweenInclusive(world.random, 100, 200);
            particle.gravity = -0.01F;
            particle.alpha = 50;
            particle.setColor(0.0F, 0.992F, 0.564F);
            return particle;
        }
    }
}

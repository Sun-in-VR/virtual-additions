package com.github.suninvr.virtualadditions.client.particle;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class GreencapSporeParticle extends SpriteBillboardParticle {
    protected GreencapSporeParticle(ClientWorld clientWorld, SpriteProvider spriteProvider, double d, double e, double f, double x, double y, double z) {
        super(clientWorld, d, e, f, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.1F + 0.8F;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.collidesWithWorld = false;
        this.velocityMultiplier = 0.997F;
        this.gravityStrength = 0.0F;
    }

    @Override
    protected int getBrightness(float tint) {
        int skyLight = 0;
        int blockLight = 0;
        BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);
        if (this.world.isChunkLoaded(blockPos)) {
            BlockState state = world.getBlockState(blockPos);
            if (state.hasEmissiveLighting(this.world, blockPos)) return 15728880;
            skyLight = this.world.getLightLevel(LightType.SKY, blockPos);
            blockLight = this.world.getLightLevel(LightType.BLOCK, blockPos);
            int luminance = state.getLuminance();
            if (blockLight < luminance) blockLight = luminance;

            skyLight = Math.max(2, skyLight) << 20;
            blockLight = Math.max(2, blockLight) << 4;
        }
        return skyLight | blockLight;
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

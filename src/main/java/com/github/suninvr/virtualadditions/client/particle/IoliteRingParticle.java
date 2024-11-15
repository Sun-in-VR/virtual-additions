package com.github.suninvr.virtualadditions.client.particle;

import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;
/**
 * Most of the code here is reused from {@link ShriekParticle}
 * **/
@SuppressWarnings("unused")
public class IoliteRingParticle extends SpriteBillboardParticle {
    private final boolean inverse;
    public IoliteRingParticle(ClientWorld clientWorld, double d, double e, double f, boolean inverse, double velocity) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.scale = 0.5F;
        this.maxAge = 8;
        this.gravityStrength = 0.0F;
        this.velocityX = 0.0;
        this.velocityY = velocity;
        this.velocityZ = 0.0;
        this.inverse = inverse;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.alpha = MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge, 0.0F, 1.0F);
        this.alpha = inverse ? this.alpha : 1.0F - this.alpha;
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationX(-1.570796327F);
        this.method_60373(vertexConsumer, camera, quaternionf, tickDelta);
        quaternionf.rotationX(-4.712388981F);
        this.method_60373(vertexConsumer, camera, quaternionf, tickDelta);
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<IoliteRingParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(IoliteRingParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            IoliteRingParticle particle = new IoliteRingParticle(world, x, y, z, parameters.inverse(), parameters.velocity());
            particle.setSprite(this.spriteProvider);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}

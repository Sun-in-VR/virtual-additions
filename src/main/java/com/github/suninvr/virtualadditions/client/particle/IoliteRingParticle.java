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
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
/**
 * Most of the code here is reused from {@link ShriekParticle}
 * **/
public class IoliteRingParticle extends SpriteBillboardParticle {
    private static final Vec3f quarternionVector = Util.make(new Vec3f(0.5F, 0.5F, 0.5F), Vec3f::normalize);
    private static final Vec3f thisIsUsedForSomethingIdkWhat = new Vec3f(-1.0F, -1.0F, 0.0F);
    private final boolean inverse;
    protected IoliteRingParticle(ClientWorld clientWorld, double d, double e, double f, boolean inverse, double velocity) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.scale = 0.5F;
        this.maxAge = 4;
        this.gravityStrength = 0.0F;
        this.velocityX = 0.0;
        this.velocityY = velocity;
        this.velocityZ = 0.0;
        this.inverse = inverse;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.alpha = this.inverse ? MathHelper.clamp(((this.age + tickDelta) / this.maxAge) - 0.25F, 0.0F, 1F) : 1 - MathHelper.clamp(((this.age + tickDelta) / this.maxAge), 0.0F, 1.0F);
        if (this.age > 2) this.velocityY = 0;
        this.buildParticleFace(vertexConsumer, camera, tickDelta, (quaternion) -> quaternion.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(1.57079F)));
        this.buildParticleFace(vertexConsumer, camera, tickDelta, (quaternion) -> quaternion.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(-1.57079F)));
    }

    private void buildParticleFace(VertexConsumer vertexConsumer, Camera camera, float f, Consumer<Quaternion> consumer) {
        Vec3d vec3d = camera.getPos();
        float g = (float)(MathHelper.lerp(f, this.prevPosX, this.x) - vec3d.getX());
        float h = (float)(MathHelper.lerp(f, this.prevPosY, this.y) - vec3d.getY());
        float i = (float)(MathHelper.lerp(f, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternion quaternion = new Quaternion(quarternionVector, 0.0F, true);
        consumer.accept(quaternion);
        //thisIsUsedForSomethingIdkWhat.rotate(quaternion);
        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, -0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float j = this.getSize(f);

        int k;
        for(k = 0; k < 4; ++k) {
            Vec3f vec3f = vec3fs[k];
            vec3f.rotate(quaternion);
            vec3f.scale(j);
            vec3f.add(g, h, i);
        }

        k = this.getBrightness(f);
        this.applyUv(vertexConsumer, vec3fs[0], this.getMaxU(), this.getMaxV(), k);
        this.applyUv(vertexConsumer, vec3fs[1], this.getMaxU(), this.getMinV(), k);
        this.applyUv(vertexConsumer, vec3fs[2], this.getMinU(), this.getMinV(), k);
        this.applyUv(vertexConsumer, vec3fs[3], this.getMinU(), this.getMaxV(), k);
    }

    private void applyUv(VertexConsumer vertexConsumer, Vec3f vec3f, float f, float g, int i) {
        vertexConsumer.vertex(vec3f.getX(), vec3f.getY(), vec3f.getZ()).texture(f, g).color(this.red, this.green, this.blue, this.alpha).light(i).next();
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

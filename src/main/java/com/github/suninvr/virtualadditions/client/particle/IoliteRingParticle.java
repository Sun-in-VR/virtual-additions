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
    private static final Vector3f quarternionVector = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f thisIsUsedForSomethingIdkWhat = new Vector3f(-1.0F, -1.0F, 0.0F);
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

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.alpha = this.inverse ? MathHelper.clamp(((this.age + tickDelta) / this.maxAge), 0.0F, 1F) : 1 - MathHelper.clamp(((this.age + tickDelta) / this.maxAge), 0.0F, 1.0F);
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> quaternion.mul((new Quaternionf()).rotationX(-1.57079F)));
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> quaternion.mul((new Quaternionf()).rotationYXZ(-3.1415927F, 1.57079F, 0.0F)));
    }

    private void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternionf> rotator) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(0.0F, quarternionVector.x(), quarternionVector.y(), quarternionVector.z());
        rotator.accept(quaternionf);
        quaternionf.transform(thisIsUsedForSomethingIdkWhat);
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float i = this.getSize(tickDelta);

        int j;
        for(j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }

        j = this.getBrightness(tickDelta);
        this.vertex(vertexConsumer, vector3fs[0], this.getMaxU(), this.getMaxV(), j);
        this.vertex(vertexConsumer, vector3fs[1], this.getMaxU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vector3fs[2], this.getMinU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vector3fs[3], this.getMinU(), this.getMaxV(), j);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.vertex(pos.x(), pos.y(), pos.z()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light);
    }

    private void applyUv(VertexConsumer vertexConsumer, Vector3f vec3f, float f, float g, int i) {
        vertexConsumer.vertex(vec3f.x, vec3f.y, vec3f.z).texture(f, g).color(this.red, this.green, this.blue, this.alpha).light(i);
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

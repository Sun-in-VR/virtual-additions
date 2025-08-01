package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.SpectralBoltEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.command.EntityRenderCommandQueue;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class SpectralBoltEntityRenderer extends EntityRenderer<SpectralBoltEntity, EntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/spectre/spectral_bolt.png");
    private static final RenderLayer LAYER;

    public SpectralBoltEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EntityRenderState renderState, MatrixStack matrices, EntityRenderCommandQueue queue) {
        super.render(renderState, matrices, queue);
        matrices.push();
        queue.pushCustom(matrices, LAYER, (matricesEntry, vertexConsumer) -> {
            float f = (30.0F - renderState.age) / 60.0F;
            matricesEntry.scale(f, f, f);
            matricesEntry.rotate(this.dispatcher.getRotation());
            matricesEntry.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            produceVertex(vertexConsumer, matricesEntry, renderState.light, 0.0F, 0, 0, 1);
            produceVertex(vertexConsumer, matricesEntry, renderState.light, 1.0F, 0, 1, 1);
            produceVertex(vertexConsumer, matricesEntry, renderState.light, 1.0F, 1, 1, 0);
            produceVertex(vertexConsumer, matricesEntry, renderState.light, 0.0F, 1, 0, 0);
        });
        matrices.pop();
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    private static void produceVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.vertex(matrix, x - 0.5F, (float)z - 0.25F, 0.0F).color(-1).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    static {
        LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    }
}

package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class AcidSpitEntityRenderer extends EntityRenderer<AcidSpitEntity, EntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/lumwasp/acid_spit.png");
    private static final RenderLayer LAYER;

    public AcidSpitEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState) {
        matrices.push();
        matrices.scale(0.5F, 0.5F, 0.5F);
        matrices.multiply(cameraRenderState.orientation);
        queue.submitCustom(matrices, LAYER, (matricesEntry, vertexConsumer) -> {
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

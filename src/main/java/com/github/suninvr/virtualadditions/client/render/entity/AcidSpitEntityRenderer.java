package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class AcidSpitEntityRenderer extends EntityRenderer<AcidSpitEntity, EntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/lumwasp/acid_spit.png");
    private static final RenderLayer LAYER;

    public AcidSpitEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(0.5F, 0.5F, 0.5F);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);
        produceVertex(vertexConsumer, entry, light, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, entry, light, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, entry, light, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, entry, light, 0.0F, 1, 0, 0);
        matrices.pop();
        super.render(state, matrices, vertexConsumers, light);
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

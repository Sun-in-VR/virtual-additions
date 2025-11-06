package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.SpectralBoltEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SpectralBoltEntityRenderer extends EntityRenderer<SpectralBoltEntity, EntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/spectre/spectral_bolt.png");
    private static final RenderType LAYER;

    public SpectralBoltEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        super.submit(renderState, matrices, queue, cameraState);
        matrices.pushPose();
        float f = (30.0F - renderState.ageInTicks) / 60.0F;
        matrices.scale(f, f, f);
        matrices.mulPose(cameraState.orientation);
        queue.submitCustomGeometry(matrices, LAYER, (matricesEntry, vertexConsumer) -> {
            produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 0.0F, 0, 0, 1);
            produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 1.0F, 0, 1, 1);
            produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 1.0F, 1, 1, 0);
            produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 0.0F, 1, 0, 0);
        });
        matrices.popPose();
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    private static void produceVertex(VertexConsumer vertexConsumer, PoseStack.Pose matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.addVertex(matrix, x - 0.5F, (float)z - 0.25F, 0.0F).setColor(-1).setUv((float)textureU, (float)textureV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }

    static {
        LAYER = RenderTypes.entityCutoutNoCull(TEXTURE);
    }
}

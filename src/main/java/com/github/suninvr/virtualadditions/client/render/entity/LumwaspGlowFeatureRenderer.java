package com.github.suninvr.virtualadditions.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unchecked")
public class LumwaspGlowFeatureRenderer<T extends LumwaspEntityRenderState, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SKIN = RenderType.eyes(ResourceLocation.fromNamespaceAndPath("virtual_additions","textures/entity/lumwasp/lumwasp_glow.png"));
    public LumwaspGlowFeatureRenderer(RenderLayerParent featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType());
        this.getParentModel().renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}

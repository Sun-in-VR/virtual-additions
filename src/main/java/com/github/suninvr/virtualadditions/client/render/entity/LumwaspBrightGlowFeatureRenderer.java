package com.github.suninvr.virtualadditions.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

@SuppressWarnings("unchecked")
public class LumwaspBrightGlowFeatureRenderer<T extends LumwaspEntityRenderState, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SKIN = RenderTypes.eyes(Identifier.fromNamespaceAndPath("virtual_additions","textures/entity/lumwasp/lumwasp_glow_bright.png"));
    public LumwaspBrightGlowFeatureRenderer(RenderLayerParent featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType());
        float value = (float) ( ((Math.sin(entity.ageInTicks * 0.25) + 1) / 8) + 0.33F);
        this.getParentModel().renderToBuffer(matrices, vertexConsumer, (int) value, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}

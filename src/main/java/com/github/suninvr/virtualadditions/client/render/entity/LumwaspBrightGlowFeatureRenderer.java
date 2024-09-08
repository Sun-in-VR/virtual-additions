package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@SuppressWarnings("unchecked")
public class LumwaspBrightGlowFeatureRenderer<T extends LumwaspEntityRenderState, M extends EntityModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(Identifier.of("virtual_additions","textures/entity/lumwasp/lumwasp_glow_bright.png"));
    public LumwaspBrightGlowFeatureRenderer(FeatureRendererContext featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getEyesTexture());
        float value = (float) ( ((Math.sin(entity.age * 0.25) + 1) / 8) + 0.33F);
        this.getContextModel().render(matrices, vertexConsumer, (int) value, OverlayTexture.DEFAULT_UV);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

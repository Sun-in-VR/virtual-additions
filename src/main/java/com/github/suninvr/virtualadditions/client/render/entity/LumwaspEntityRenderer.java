package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LumwaspEntityRenderer extends MobRenderer<LumwaspEntity, LumwaspEntityRenderState, LumwaspEntityModel<LumwaspEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("virtual_additions", "textures/entity/lumwasp/lumwasp.png");

    public LumwaspEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LumwaspEntityModel<>(context.bakeLayer(VARenderers.LUMWASP_LAYER)), 0.75F);
        this.addLayer(new LumwaspGlowFeatureRenderer(this));
        this.addLayer(new LumwaspBrightGlowFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LumwaspEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void extractRenderState(LumwaspEntity livingEntity, LumwaspEntityRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.inAir = livingEntity.isFlying();
    }

    @Override
    public LumwaspEntityRenderState createRenderState() {
        return new LumwaspEntityRenderState();
    }
}

package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LumwaspEntityRenderer extends MobEntityRenderer<LumwaspEntity, LumwaspEntityRenderState, LumwaspEntityModel<LumwaspEntity>> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/lumwasp/lumwasp.png");

    public LumwaspEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LumwaspEntityModel<>(context.getPart(VARenderers.LUMWASP_LAYER)), 0.75F);
        this.addFeature(new LumwaspGlowFeatureRenderer(this));
        this.addFeature(new LumwaspBrightGlowFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(LumwaspEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(LumwaspEntity livingEntity, LumwaspEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.inAir = livingEntity.isInAir();
    }

    @Override
    public LumwaspEntityRenderState createRenderState() {
        return new LumwaspEntityRenderState();
    }
}

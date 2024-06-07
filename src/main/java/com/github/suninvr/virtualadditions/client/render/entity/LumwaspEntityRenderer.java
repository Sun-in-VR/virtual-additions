package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LumwaspEntityRenderer extends MobEntityRenderer {
    public LumwaspEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LumwaspEntityModel<>(context.getPart(VARenderers.LUMWASP_LAYER)), 0.75F);
        this.addFeature(new LumwaspGlowFeatureRenderer(this));
        this.addFeature(new LumwaspBrightGlowFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return Identifier.of("virtual_additions", "textures/entity/lumwasp/lumwasp.png");
    }
}

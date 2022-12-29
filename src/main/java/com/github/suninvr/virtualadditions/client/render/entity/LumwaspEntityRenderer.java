package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VirtualAdditionsClient;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class LumwaspEntityRenderer<T extends LumwaspEntity> extends MobEntityRenderer {
    public LumwaspEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LumwaspEntityModel<LumwaspEntity>(context.getPart(VirtualAdditionsClient.LUMWASP_LAYER)), 0.75F);
        this.addFeature(new LumwaspGlowFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return new Identifier("virtual_additions", "textures/entity/lumwasp/lumwasp.png");
    }
}

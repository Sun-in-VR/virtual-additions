package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.LyftEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LyftEntityRenderer extends MobEntityRenderer<LyftEntity, LyftEntityModel<LyftEntity>> {
    public LyftEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LyftEntityModel<>(ctx.getPart(VARenderers.LYFT_LAYER)), 0.75F);
    }

    @Override
    public Identifier getTexture(LyftEntity entity) {
        return Identifier.of("virtual_additions", "textures/entity/lyft.png");
    }
}

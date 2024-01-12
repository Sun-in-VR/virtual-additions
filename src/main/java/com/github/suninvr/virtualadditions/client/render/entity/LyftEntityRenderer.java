package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VirtualAdditionsClient;
import com.github.suninvr.virtualadditions.entity.LyftEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class LyftEntityRenderer extends MobEntityRenderer<LyftEntity, LyftEntityModel<LyftEntity>> {
    public LyftEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LyftEntityModel<>(ctx.getPart(VirtualAdditionsClient.LYFT_ENTITY_LAYER)), 0.75F);
    }

    @Override
    public Identifier getTexture(LyftEntity entity) {
        return new Identifier("virtual_additions", "textures/entity/lyft.png");
    }
}

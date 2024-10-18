package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.LyftEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class LyftEntityRenderer extends MobEntityRenderer<LyftEntity, LivingEntityRenderState, LyftEntityModel<LyftEntity>> {
    private final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/lyft.png");

    public LyftEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LyftEntityModel<>(ctx.getPart(VARenderers.LYFT_LAYER)), 0.75F);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}

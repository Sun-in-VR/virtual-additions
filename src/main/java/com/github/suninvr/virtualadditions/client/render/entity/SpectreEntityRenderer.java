package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SpectreEntityRenderer extends MobEntityRenderer<SpectreEntity, LivingEntityRenderState, SpectreEntityModel<SpectreEntity>> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/spectre/spectre.png");

    public SpectreEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SpectreEntityModel(context.getPart(VARenderers.SPECTRE_LAYER)), 0.25F);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLight(SpectreEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}

package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SpectreEntityRenderer extends MobRenderer<SpectreEntity, LivingEntityRenderState, SpectreEntityModel<SpectreEntity>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/spectre/spectre.png");

    public SpectreEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SpectreEntityModel(context.bakeLayer(VARenderers.SPECTRE_LAYER)), 0.25F);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(SpectreEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}

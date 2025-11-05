package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ClimbingRopeEntityRenderer extends ArrowRenderer<ClimbingRopeEntity, ClimbingRopeEntityRenderState> {
    public static final ResourceLocation UNAFFECTED_TEXTURE = ResourceLocation.fromNamespaceAndPath(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/climbing_rope.png");
    public static final ResourceLocation EXPOSED_TEXTURE = ResourceLocation.fromNamespaceAndPath(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/exposed_climbing_rope.png");
    public static final ResourceLocation WEATHERED_TEXTURE = ResourceLocation.fromNamespaceAndPath(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/weathered_climbing_rope.png");
    public static final ResourceLocation OXIDIZED_TEXTURE = ResourceLocation.fromNamespaceAndPath(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/oxidized_climbing_rope.png");

    public ClimbingRopeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ClimbingRopeEntityRenderState createRenderState() {
        return new ClimbingRopeEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(ClimbingRopeEntityRenderState state) {
        return switch (state.oxidationLevel) {
            case UNAFFECTED -> UNAFFECTED_TEXTURE;
            case EXPOSED -> EXPOSED_TEXTURE;
            case WEATHERED -> WEATHERED_TEXTURE;
            case OXIDIZED -> OXIDIZED_TEXTURE;
        };
    }

    @Override
    public void extractRenderState(ClimbingRopeEntity persistentProjectileEntity, ClimbingRopeEntityRenderState projectileEntityRenderState, float f) {
        projectileEntityRenderState.oxidationLevel = persistentProjectileEntity.getOxidationLevel();
        super.extractRenderState(persistentProjectileEntity, projectileEntityRenderState, f);
    }
}

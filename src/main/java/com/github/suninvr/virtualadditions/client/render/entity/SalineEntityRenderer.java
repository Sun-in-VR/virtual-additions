package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class SalineEntityRenderer extends ZombieRenderer {
    private static final Identifier TEXTURE = idOf("textures/entity/saline.png");

    public SalineEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState zombieEntityRenderState) {
        return TEXTURE;
    }
}

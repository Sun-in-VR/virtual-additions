package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ClimbingRopeEntityRenderer extends ProjectileEntityRenderer<ClimbingRopeEntity> {
    public static final Identifier TEXTURE = new Identifier(VirtualAdditions.MODID, "textures/entity/projectiles/climbing_rope.png");

    public ClimbingRopeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ClimbingRopeEntity entity) {
        return TEXTURE;
    }
}

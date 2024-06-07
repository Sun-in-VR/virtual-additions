package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ClimbingRopeEntityRenderer extends ProjectileEntityRenderer<ClimbingRopeEntity> {
    public static final Identifier UNAFFECTED_TEXTURE = Identifier.of(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/climbing_rope.png");
    public static final Identifier EXPOSED_TEXTURE = Identifier.of(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/exposed_climbing_rope.png");
    public static final Identifier WEATHERED_TEXTURE = Identifier.of(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/weathered_climbing_rope.png");
    public static final Identifier OXIDIZED_TEXTURE = Identifier.of(VirtualAdditions.NAMESPACE, "textures/entity/projectiles/oxidized_climbing_rope.png");

    public ClimbingRopeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ClimbingRopeEntity entity) {
        return switch (entity.getOxidation()) {
            case UNAFFECTED -> UNAFFECTED_TEXTURE;
            case EXPOSED -> EXPOSED_TEXTURE;
            case WEATHERED -> WEATHERED_TEXTURE;
            case OXIDIZED -> OXIDIZED_TEXTURE;
        };
    }
}

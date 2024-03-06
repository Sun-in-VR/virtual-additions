package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.SalineEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class SalineEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE = idOf("textures/entity/saline.png");

    public SalineEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void scale(ZombieEntity zombieEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375f;
        matrixStack.scale(g, g, g);
        super.scale(zombieEntity, matrixStack, f);
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }
}

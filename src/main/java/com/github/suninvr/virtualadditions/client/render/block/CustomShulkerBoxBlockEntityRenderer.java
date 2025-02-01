package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class CustomShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
    private final ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel model;

    public CustomShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel(ctx.getLayerModelPart(EntityModelLayers.SHULKER_BOX));
    }

    @Override
    public void render(ShulkerBoxBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        SpriteIdentifier spriteIdentifier = entity.getColor() == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : VADyeColors.getShulkerBoxTexture(entity.getColor());
        spriteIdentifier = spriteIdentifier == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : spriteIdentifier;
        Direction direction = entity.getCachedState().get(ShulkerBoxBlock.FACING, Direction.UP);

        float g = entity.getAnimationProgress(tickProgress);
        this.render(matrices, vertexConsumers, light, overlay, direction, g, spriteIdentifier);

    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Direction facing, float openness, SpriteIdentifier textureId) {
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.scale(0.9995F, 0.9995F, 0.9995F);
        matrices.multiply(facing.getRotationQuaternion());
        matrices.scale(1.0F, -1.0F, -1.0F);
        matrices.translate(0.0F, -1.0F, 0.0F);
        this.model.animateLid(openness);
        ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel model = this.model;
        Objects.requireNonNull(model);
        VertexConsumer vertexConsumer = textureId.getVertexConsumer(vertexConsumers, model::getLayer);
        this.model.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}

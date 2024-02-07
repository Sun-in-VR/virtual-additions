package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.block.entity.CustomShulkerBoxBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

public class CustomShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<CustomShulkerBoxBlockEntity> {
    private final ShulkerEntityModel<?> model;

    public CustomShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ShulkerEntityModel<>(ctx.getLayerModelPart(EntityModelLayers.SHULKER));
    }
    
    @Override
    public void render(CustomShulkerBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState blockState;
        Direction direction = Direction.UP;
        if (entity.hasWorld() && (blockState = entity.getWorld().getBlockState(entity.getPos())).getBlock() instanceof ShulkerBoxBlock) {
            direction = blockState.get(ShulkerBoxBlock.FACING);
        }
        SpriteIdentifier spriteIdentifier = entity.getExtendedDyeColor() == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : entity.getExtendedDyeColor().getShulkerBoxTexture();
        spriteIdentifier = spriteIdentifier == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : spriteIdentifier;
        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.scale(0.9995f, 0.9995f, 0.9995f);
        matrices.multiply(direction.getRotationQuaternion());
        matrices.scale(1.0f, -1.0f, -1.0f);
        matrices.translate(0.0f, -1.0f, 0.0f);
        ModelPart modelPart = this.model.getLid();
        modelPart.setPivot(0.0f, 24.0f - entity.getAnimationProgress(tickDelta) * 0.5f * 16.0f, 0.0f);
        modelPart.yaw = 270.0f * entity.getAnimationProgress(tickDelta) * ((float)Math.PI / 180);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutoutNoCull);
        this.model.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
    }
}

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

import java.util.Objects;

public class CustomShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
    private final ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel model;

    public CustomShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel(ctx.getLayerModelPart(EntityModelLayers.SHULKER));
    }
    
    @Override
    public void render(ShulkerBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SpriteIdentifier spriteIdentifier = entity.getColor() == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : VADyeColors.getShulkerBoxTexture(entity.getColor());
        spriteIdentifier = spriteIdentifier == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : spriteIdentifier;


        Direction direction = Direction.UP;
        if (entity.hasWorld()) {
            BlockState blockState = entity.getWorld().getBlockState(entity.getPos());
            if (blockState.getBlock() instanceof ShulkerBoxBlock) {
                direction = blockState.get(ShulkerBoxBlock.FACING);
            }
        }

        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        float g = 0.9995F;
        matrices.scale(0.9995F, 0.9995F, 0.9995F);
        matrices.multiply(direction.getRotationQuaternion());
        matrices.scale(1.0F, -1.0F, -1.0F);
        matrices.translate(0.0F, -1.0F, 0.0F);
        this.model.animateLid(entity, tickDelta);
        ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel var10002 = this.model;
        Objects.requireNonNull(var10002);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, var10002::getLayer);
        this.model.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}

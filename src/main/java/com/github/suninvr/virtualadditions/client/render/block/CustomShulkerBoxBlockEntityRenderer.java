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
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
    private final ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel model;

    public CustomShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ShulkerBoxBlockEntityRenderer.ShulkerBoxBlockModel(ctx.getLayerModelPart(EntityModelLayers.SHULKER_BOX));
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Direction facing, float openness, SpriteIdentifier textureId) {

    }

    @Override
    public void render(ShulkerBoxBlockEntity entity, float tickProgress, MatrixStack matrices, int light, int overlay, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, OrderedRenderCommandQueue orderedRenderCommandQueue) {

    }
}

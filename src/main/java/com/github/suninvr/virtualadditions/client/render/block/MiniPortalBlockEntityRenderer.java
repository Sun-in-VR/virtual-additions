package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.client.VARenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class MiniPortalBlockEntityRenderer implements BlockEntityRenderer<MiniPortalBlockEntity> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/mini_portal/open.png");
    private static final Identifier TEXTURE_BLOCKED = Identifier.of("virtual_additions", "textures/entity/mini_portal/blocked.png");
    private static final Identifier TEXTURE_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/open_glow.png");
    private static final Identifier TEXTURE_BLOCKED_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/blocked_glow.png");
    private static final Identifier TEXTURE_DYED = Identifier.of("virtual_additions", "textures/entity/mini_portal/dyed_open.png");
    private static final Identifier TEXTURE_DYED_BLOCKED = Identifier.of("virtual_additions", "textures/entity/mini_portal/dyed_blocked.png");
    private static final Identifier TEXTURE_DYED_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/dyed_open_glow.png");
    private static final Identifier TEXTURE_DYED_BLOCKED_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/dyed_blocked_glow.png");
    private static final RenderLayer LAYER = VARenderLayers.getMiniPortal(TEXTURE, false);
    private static final RenderLayer LAYER_BLOCKED = VARenderLayers.getMiniPortal(TEXTURE_BLOCKED, false);
    private static final RenderLayer LAYER_GLOW = VARenderLayers.getMiniPortal(TEXTURE_GLOW, true);
    private static final RenderLayer LAYER_BLOCKED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_BLOCKED_GLOW, true);
    private static final RenderLayer LAYER_DYED = VARenderLayers.getMiniPortal(TEXTURE_DYED, false);
    private static final RenderLayer LAYER_DYED_BLOCKED = VARenderLayers.getMiniPortal(TEXTURE_DYED_BLOCKED, false);
    private static final RenderLayer LAYER_DYED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_DYED_GLOW, true);
    private static final RenderLayer LAYER_DYED_BLOCKED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_DYED_BLOCKED_GLOW, true);

    public MiniPortalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(MiniPortalBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        renderPortal(entity, tickProgress, matrices, vertexConsumers, 255, overlay, cameraPos, getGlowLayer(entity));
        renderPortal(entity, tickProgress, matrices, vertexConsumers, 255, overlay, cameraPos, getBaseLayer(entity));
    }

    private static RenderLayer getGlowLayer(MiniPortalBlockEntity blockEntity) {
        boolean dyed = blockEntity.isDyed();
        if (dyed) return blockEntity.isBlocked() ? LAYER_DYED_BLOCKED_GLOW : LAYER_DYED_GLOW;
        return blockEntity.isBlocked() ? LAYER_BLOCKED_GLOW : LAYER_GLOW;
    }

    private static RenderLayer getBaseLayer(MiniPortalBlockEntity blockEntity) {
        boolean dyed = blockEntity.isDyed();
        if (dyed) return blockEntity.isBlocked() ? LAYER_DYED_BLOCKED : LAYER_DYED;
        return blockEntity.isBlocked() ? LAYER_BLOCKED : LAYER;
    }

    private static void renderPortal(MiniPortalBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos, RenderLayer layer) {
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());
        MatrixStack.Entry entry = matrices.peek();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
        int color = entity.getDyeColor();
        producePortalVertex(vertexConsumer, entry, light, 0, 0, 0, 1, color);
        producePortalVertex(vertexConsumer, entry, light, 1, 0, 1, 1, color);
        producePortalVertex(vertexConsumer, entry, light, 1, 1, 1, 0, color);
        producePortalVertex(vertexConsumer, entry, light, 0, 1, 0, 0, color);
        matrices.pop();
    }


    private static void producePortalVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV, int color) {
        vertexConsumer.vertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).color(color).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }
}

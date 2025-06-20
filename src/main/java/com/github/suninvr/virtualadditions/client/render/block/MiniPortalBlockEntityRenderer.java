package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MiniPortalBlockEntityRenderer implements BlockEntityRenderer<MiniPortalBlockEntity> {
    private static final Identifier TEXTURE = Identifier.of("virtual_additions", "textures/entity/mini_portal/open.png");
    private static final Identifier TEXTURE_BLOCKED = Identifier.of("virtual_additions", "textures/entity/mini_portal/blocked.png");
    private static final Identifier TEXTURE_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/open_glow.png");
    private static final Identifier TEXTURE_BLOCKED_GLOW = Identifier.of("virtual_additions", "textures/entity/mini_portal/blocked_glow.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);
    private static final RenderLayer LAYER_BLOCKED = RenderLayer.getEntityCutout(TEXTURE_BLOCKED);
    private static final RenderLayer LAYER_GLOW = RenderLayer.getEntityTranslucent(TEXTURE_GLOW);
    private static final RenderLayer LAYER_BLOCKED_GLOW = RenderLayer.getEntityTranslucent(TEXTURE_BLOCKED_GLOW);

    public MiniPortalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(MiniPortalBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        renderPortal(entity, tickProgress, matrices, vertexConsumers, 255, overlay, cameraPos, entity.isBlocked() ? LAYER_BLOCKED_GLOW : LAYER_GLOW);
        renderPortal(entity, tickProgress, matrices, vertexConsumers, 255, overlay, cameraPos, entity.isBlocked() ? LAYER_BLOCKED : LAYER);
    }

    private static void renderPortal(MiniPortalBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos, RenderLayer layer) {
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());
        MatrixStack.Entry entry = matrices.peek();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
        produceVertex(vertexConsumer, entry, light, 0, 0, 0, 1);
        produceVertex(vertexConsumer, entry, light, 1, 0, 1, 1);
        produceVertex(vertexConsumer, entry, light, 1, 1, 1, 0);
        produceVertex(vertexConsumer, entry, light, 0, 1, 0, 0);
        matrices.pop();
    }


    private static void produceVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.vertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).color(-1).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }
}

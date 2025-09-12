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
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MiniPortalBlockEntityRenderer implements BlockEntityRenderer<MiniPortalBlockEntity, MiniPortalBlockEntityRenderState> {
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

    private static RenderLayer getGlowLayer(MiniPortalBlockEntityRenderState state) {
        boolean dyed = state.dyeColor.isPresent();
        if (dyed) return state.blocked ? LAYER_DYED_BLOCKED_GLOW : LAYER_DYED_GLOW;
        return state.blocked ? LAYER_BLOCKED_GLOW : LAYER_GLOW;
    }

    private static RenderLayer getBaseLayer(MiniPortalBlockEntityRenderState state) {
        boolean dyed = state.dyeColor.isPresent();
        if (dyed) return state.blocked ? LAYER_DYED_BLOCKED : LAYER_DYED;
        return state.blocked ? LAYER_BLOCKED : LAYER;
    }

    private static void renderPortal(MiniPortalBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue renderCommandQueue, RenderLayer layer) {
        renderCommandQueue.submitCustom(matrices, layer, (matricesEntry, vertexConsumer) -> {
            matricesEntry.translate(0.5F, 0.5F, 0.5F);
            matricesEntry.rotate(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());
            int color = state.dyeColor.map(DyeColor::getEntityColor).orElse(-1);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 0, 0, 0, 1, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 1, 0, 1, 1, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 1, 1, 1, 0, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 0, 1, 0, 0, color);
        });
    }


    private static void producePortalVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV, int color) {
        vertexConsumer.vertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).color(color).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public MiniPortalBlockEntityRenderState createRenderState() {
        return new MiniPortalBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(MiniPortalBlockEntity blockEntity, MiniPortalBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.blocked = blockEntity.isBlocked();
        state.dyeColor = Optional.ofNullable(blockEntity.getDyeColor());
    }

    @Override
    public void render(MiniPortalBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue) {
        renderPortal(state, matrices, queue, getGlowLayer(state));
        renderPortal(state, matrices, queue, getBaseLayer(state));
    }
}

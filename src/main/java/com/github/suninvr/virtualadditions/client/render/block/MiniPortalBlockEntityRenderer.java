package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.client.VARenderLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MiniPortalBlockEntityRenderer implements BlockEntityRenderer<MiniPortalBlockEntity, MiniPortalBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/open.png");
    private static final Identifier TEXTURE_BLOCKED = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/blocked.png");
    private static final Identifier TEXTURE_GLOW = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/open_glow.png");
    private static final Identifier TEXTURE_BLOCKED_GLOW = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/blocked_glow.png");
    private static final Identifier TEXTURE_DYED = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/dyed_open.png");
    private static final Identifier TEXTURE_DYED_BLOCKED = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/dyed_blocked.png");
    private static final Identifier TEXTURE_DYED_GLOW = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/dyed_open_glow.png");
    private static final Identifier TEXTURE_DYED_BLOCKED_GLOW = Identifier.fromNamespaceAndPath("virtual_additions", "textures/entity/mini_portal/dyed_blocked_glow.png");
    private static final RenderType LAYER = VARenderLayers.getMiniPortal(TEXTURE, false);
    private static final RenderType LAYER_BLOCKED = VARenderLayers.getMiniPortal(TEXTURE_BLOCKED, false);
    private static final RenderType LAYER_GLOW = VARenderLayers.getMiniPortal(TEXTURE_GLOW, true);
    private static final RenderType LAYER_BLOCKED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_BLOCKED_GLOW, true);
    private static final RenderType LAYER_DYED = VARenderLayers.getMiniPortal(TEXTURE_DYED, false);
    private static final RenderType LAYER_DYED_BLOCKED = VARenderLayers.getMiniPortal(TEXTURE_DYED_BLOCKED, false);
    private static final RenderType LAYER_DYED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_DYED_GLOW, true);
    private static final RenderType LAYER_DYED_BLOCKED_GLOW = VARenderLayers.getMiniPortal(TEXTURE_DYED_BLOCKED_GLOW, true);

    public MiniPortalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private static RenderType getGlowLayer(MiniPortalBlockEntityRenderState state) {
        boolean dyed = state.dyeColor.isPresent();
        if (dyed) return state.blocked ? LAYER_DYED_BLOCKED_GLOW : LAYER_DYED_GLOW;
        return state.blocked ? LAYER_BLOCKED_GLOW : LAYER_GLOW;
    }

    private static RenderType getBaseLayer(MiniPortalBlockEntityRenderState state) {
        boolean dyed = state.dyeColor.isPresent();
        if (dyed) return state.blocked ? LAYER_DYED_BLOCKED : LAYER_DYED;
        return state.blocked ? LAYER_BLOCKED : LAYER;
    }

    private static void renderPortal(MiniPortalBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector renderCommandQueue, CameraRenderState cameraRenderState, RenderType layer) {
        renderCommandQueue.submitCustomGeometry(matrices, layer, (matricesEntry, vertexConsumer) -> {
            matricesEntry.translate(0.5F, 0.5F, 0.5F);
            matricesEntry.rotate(cameraRenderState.orientation);
            int color = state.dyeColor.map(DyeColor::getTextureDiffuseColor).orElse(-1);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 0, 0, 0, 1, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 1, 0, 1, 1, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 1, 1, 1, 0, color);
            producePortalVertex(vertexConsumer, matricesEntry, 255, 0, 1, 0, 0, color);
        });
    }


    private static void producePortalVertex(VertexConsumer vertexConsumer, PoseStack.Pose matrix, int light, float x, int z, int textureU, int textureV, int color) {
        vertexConsumer.addVertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).setColor(color).setUv((float)textureU, (float)textureV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public MiniPortalBlockEntityRenderState createRenderState() {
        return new MiniPortalBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(MiniPortalBlockEntity blockEntity, MiniPortalBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.blocked = blockEntity.isBlocked();
        state.dyeColor = Optional.ofNullable(blockEntity.getDyeColor());
    }

    @Override
    public void submit(MiniPortalBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraRenderState) {
        renderPortal(state, matrices, queue, cameraRenderState, getGlowLayer(state));
        renderPortal(state, matrices, queue, cameraRenderState, getBaseLayer(state));
    }
}

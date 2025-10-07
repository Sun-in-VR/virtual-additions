package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.client.VARenderLayers;
import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PlayerProjectionEntityRenderer<T extends PlayerProjectionEntity> extends LivingEntityRenderer<PlayerProjectionEntity, PlayerProjectionEntityRenderState, PlayerProjectionEntityModel> {
    private static final Identifier GLOW_TEXTURE_ID = VirtualAdditions.idOf("textures/entity/player_projection/glow.png");

    public PlayerProjectionEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerProjectionEntityModel(context.getPart(VARenderers.PLAYER_PROJECTION_LAYER)), 0.25F);
    }

    @Override
    public void render(PlayerProjectionEntityRenderState livingEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        super.render(livingEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
        RenderLayer glowRenderLayer = this.getGlowRenderLayer();
        if (glowRenderLayer != null) {
            orderedRenderCommandQueue.submitCustom(matrixStack, glowRenderLayer, (matricesEntry, vertexConsumer) -> {
                matricesEntry.translate(0, 0.25f, 0);
                matricesEntry.rotate(cameraRenderState.orientation);
                matricesEntry.scale(2.0F, 2.0F, 2.0F);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 0, 0, 0, 1);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 1, 0, 1, 1);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 1, 1, 1, 0);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 0, 1, 0, 0);
            });
        }
    }

    private static void produceGlowVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.vertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).color(-1).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public void updateRenderState(PlayerProjectionEntity livingEntity, PlayerProjectionEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        if (livingEntity.getPlayer() instanceof ClientPlayerLikeEntity playerLike) {
            livingEntityRenderState.skinTextures = playerLike.getSkin();
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            livingEntityRenderState.invisibleToPlayer = livingEntity.getPlayer().isInvisible() && livingEntity.getPlayer().isInvisibleTo(minecraftClient.player);
            livingEntityRenderState.hatVisible = livingEntity.getPlayer().isModelPartVisible(PlayerModelPart.HAT);
        }
    }

    @Override
    protected @Nullable Text getDisplayName(PlayerProjectionEntity entity) {
        return super.getDisplayName(entity);
    }

    @Override
    protected boolean hasLabel(PlayerProjectionEntity entity, double d) {
        return super.hasLabel(entity, d) && (entity.shouldRenderName() || entity.hasCustomName() && entity == this.dispatcher.targetedEntity);
    }

    @Override
    public Identifier getTexture(PlayerProjectionEntityRenderState state) {
        return state.skinTextures.body().texturePath();
    }

    @Override
    protected @Nullable RenderLayer getRenderLayer(PlayerProjectionEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(state);
        return RenderLayer.getEntityTranslucent(identifier, true);
    }

    protected @Nullable RenderLayer getGlowRenderLayer() {
        return VARenderLayers.getMiniPortal(GLOW_TEXTURE_ID, true);
    }

    @Override
    protected int getBlockLight(PlayerProjectionEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public PlayerProjectionEntityRenderState createRenderState() {
        return new PlayerProjectionEntityRenderState();
    }
}

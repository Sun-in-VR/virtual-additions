package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.client.VARenderLayers;
import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PlayerProjectionEntityRenderer<T extends PlayerProjectionEntity> extends LivingEntityRenderer<PlayerProjectionEntity, PlayerProjectionEntityRenderState, PlayerProjectionEntityModel> {
    private static final ResourceLocation GLOW_TEXTURE_ID = VirtualAdditions.idOf("textures/entity/player_projection/glow.png");

    public PlayerProjectionEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerProjectionEntityModel(context.bakeLayer(VARenderers.PLAYER_PROJECTION_LAYER)), 0.25F);
    }

    @Override
    public void submit(PlayerProjectionEntityRenderState livingEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(livingEntityRenderState, poseStack, submitNodeCollector, cameraRenderState);
        RenderType glowRenderLayer = this.getGlowRenderLayer();
        if (glowRenderLayer != null) {
            submitNodeCollector.submitCustomGeometry(poseStack, glowRenderLayer, (matricesEntry, vertexConsumer) -> {
                matricesEntry.translate(0, 0.25f, 0);
                matricesEntry.rotate(cameraRenderState.orientation);
                matricesEntry.scale(2.0F, 2.0F, 2.0F);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 0, 0, 0, 1);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 1, 0, 1, 1);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 1, 1, 1, 0);
                produceGlowVertex(vertexConsumer, matricesEntry, 255, 0, 1, 0, 0);
            });
        }    }


    private static void produceGlowVertex(VertexConsumer vertexConsumer, PoseStack.Pose matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.addVertex(matrix, x - 0.5F, (float)z - 0.5F, 0.0F).setColor(-1).setUv((float)textureU, (float)textureV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public void extractRenderState(PlayerProjectionEntity livingEntity, PlayerProjectionEntityRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        if (livingEntity.getPlayer() instanceof ClientAvatarEntity playerLike) {
            livingEntityRenderState.skinTextures = playerLike.getSkin();
            Minecraft minecraftClient = Minecraft.getInstance();
            livingEntityRenderState.isInvisibleToPlayer = livingEntity.getPlayer().isInvisible() && livingEntity.getPlayer().isInvisibleTo(minecraftClient.player);
            livingEntityRenderState.hatVisible = livingEntity.getPlayer().isModelPartShown(PlayerModelPart.HAT);
        }
    }
    @Override
    protected @Nullable Component getNameTag(PlayerProjectionEntity entity) {
        return super.getNameTag(entity);
    }

    @Override
    protected boolean shouldShowName(PlayerProjectionEntity entity, double d) {
        return super.shouldShowName(entity, d) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(PlayerProjectionEntityRenderState state) {
        return state.skinTextures.body().texturePath();
    }

    @Override
    protected @Nullable RenderType getRenderType(PlayerProjectionEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        ResourceLocation identifier = this.getTextureLocation(state);
        return RenderType.entityTranslucent(identifier, true);
    }

    protected @Nullable RenderType getGlowRenderLayer() {
        return VARenderLayers.getMiniPortal(GLOW_TEXTURE_ID, true);
    }

    @Override
    protected int getBlockLightLevel(PlayerProjectionEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public PlayerProjectionEntityRenderState createRenderState() {
        return new PlayerProjectionEntityRenderState();
    }
}

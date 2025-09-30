package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PlayerProjectionEntityRenderer<T extends PlayerProjectionEntity> extends LivingEntityRenderer<PlayerProjectionEntity, PlayerProjectionEntityRenderState, PlayerProjectionEntityModel> {

    public PlayerProjectionEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerProjectionEntityModel(context.getPart(VARenderers.PLAYER_PROJECTION_LAYER)), 0.25F);
    }

    @Override
    public void updateRenderState(PlayerProjectionEntity livingEntity, PlayerProjectionEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.invisible = true;
        livingEntityRenderState.invisibleToPlayer = false;
        if (livingEntity.getPlayer() instanceof ClientPlayerLikeEntity playerLike) {
            livingEntityRenderState.skinTextures = playerLike.getSkin();
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            livingEntityRenderState.invisibleToPlayer = livingEntity.getPlayer().isInvisible() && livingEntity.getPlayer().isInvisibleTo(minecraftClient.player);
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
        return RenderLayer.getItemEntityTranslucentCull(identifier);
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

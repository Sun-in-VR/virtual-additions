package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(Gui.class)
public class InGameHudMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow private float scopeScale;
    @Unique
    private static final Identifier SPECTRAL_SPYGLASS_SCOPE = idOf("textures/misc/spectral_spyglass_scope.png");

    @Unique
    private static final Identifier SPECTRAL_FULL_HEART_TEXTURE = VirtualAdditions.idOf("hud/heart/heart/spectral_full");

    @Unique
    private static final Identifier SPECTRAL_HALF_HEART_TEXTURE = VirtualAdditions.idOf("hud/heart/heart/spectral_half");

    @Unique
    private boolean isPlayerProjection;

    @Unique
    private void virtualAdditions$renderSpectralSpyglassOverlay(GuiGraphics context, float scale) {
        float f = (float)Math.min(context.guiWidth(), context.guiHeight());
        float h = Math.min((float)context.guiWidth() / f, (float)context.guiHeight() / f) * scale;
        int i = Mth.floor(f * h);
        int j = Mth.floor(f * h);
        int k = (context.guiWidth() - i) / 2;
        int l = (context.guiHeight() - j) / 2;
        int m = k + i;
        int n = l + j;
        context.blit(RenderPipelines.GUI_TEXTURED, SPECTRAL_SPYGLASS_SCOPE, k, l, 0.0F, 0.0F, i, j, i, j);
        context.fill(RenderPipelines.GUI, 0, n, context.guiWidth(), context.guiHeight(), -16777216);
        context.fill(RenderPipelines.GUI, 0, 0, context.guiWidth(), l, -16777216);
        context.fill(RenderPipelines.GUI, 0, l, k, n, -16777216);
        context.fill(RenderPipelines.GUI, m, l, context.guiWidth(), n, -16777216);
    }

    @Inject(method = "getPlayerVehicleWithHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getVehicle()Lnet/minecraft/world/entity/Entity;"), cancellable = true)
    void virtualAdditions$getPlayerProjectionEntity(CallbackInfoReturnable<LivingEntity> cir) {
        if (minecraft.getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity && !playerProjectionEntity.isInvulnerable()) {
            cir.setReturnValue(playerProjectionEntity);
            this.isPlayerProjection = true;
        } else {
            this.isPlayerProjection = false;
        }
    }

    @ModifyArg(method = "renderVehicleHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 1), index = 1)
    Identifier virtualAdditions$renderProjectionHeart(Identifier sprite) {
        if (this.isPlayerProjection) {
            return SPECTRAL_FULL_HEART_TEXTURE;
        }
        return sprite;
    }

    @ModifyArg(method = "renderVehicleHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2), index = 1)
    Identifier virtualAdditions$renderProjectionHalfHeart(Identifier sprite) {
        if (this.isPlayerProjection) {
            return SPECTRAL_HALF_HEART_TEXTURE;
        }
        return sprite;
    }

    @Inject(method = "renderCameraOverlays", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$renderSpectralSpyglassMiscOverlay(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (this.minecraft.options.getCameraType().isFirstPerson() && ProjectionSpyglassItem.isInUseBy(this.minecraft.player)) {
            float f = tickCounter.getGameTimeDeltaTicks();
            this.scopeScale = Mth.lerp(0.5F * f, this.scopeScale, 1.125F);
            this.virtualAdditions$renderSpectralSpyglassOverlay(context, this.scopeScale);
            ci.cancel();
        }
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getCameraPlayerFromProjection(CallbackInfoReturnable<Player> cir) {
        if (this.minecraft.getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity && ProjectionSpyglassItem.isInUseBy(this.minecraft.player)) cir.setReturnValue(playerProjectionEntity.getPlayer());
    }
}

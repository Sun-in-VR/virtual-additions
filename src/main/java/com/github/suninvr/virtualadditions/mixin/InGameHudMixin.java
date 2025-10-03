package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
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

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private float spyglassScale;
    @Unique
    private static final Identifier SPECTRAL_SPYGLASS_SCOPE = idOf("textures/misc/spectral_spyglass_scope.png");

    @Unique
    private static final Identifier SPECTRAL_FULL_HEART_TEXTURE = VirtualAdditions.idOf("hud/heart/heart/spectral_full");

    @Unique
    private static final Identifier SPECTRAL_HALF_HEART_TEXTURE = VirtualAdditions.idOf("hud/heart/heart/spectral_half");

    @Unique
    private void virtualAdditions$renderSpectralSpyglassOverlay(DrawContext context, float scale) {
        float f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / f) * scale;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(f * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;
        int m = k + i;
        int n = l + j;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, SPECTRAL_SPYGLASS_SCOPE, k, l, 0.0F, 0.0F, i, j, i, j);
        context.fill(RenderPipelines.GUI, 0, n, context.getScaledWindowWidth(), context.getScaledWindowHeight(), -16777216);
        context.fill(RenderPipelines.GUI, 0, 0, context.getScaledWindowWidth(), l, -16777216);
        context.fill(RenderPipelines.GUI, 0, l, k, n, -16777216);
        context.fill(RenderPipelines.GUI, m, l, context.getScaledWindowWidth(), n, -16777216);
    }

    @Inject(method = "getRiddenEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getVehicle()Lnet/minecraft/entity/Entity;"), cancellable = true)
    void virtualAdditions$getPlayerProjectionEntity(CallbackInfoReturnable<LivingEntity> cir) {
        if (client.getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity) {
            cir.setReturnValue(playerProjectionEntity);
        }
    }

    @ModifyArg(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), index = 1)
    Identifier virtualAdditions$renderProjectionHeart(Identifier sprite) {
        if (client.getCameraEntity() instanceof PlayerProjectionEntity) {
            return SPECTRAL_FULL_HEART_TEXTURE;
        }
        return sprite;
    }

    @ModifyArg(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 2), index = 1)
    Identifier virtualAdditions$renderProjectionHalfHeart(Identifier sprite) {
        if (client.getCameraEntity() instanceof PlayerProjectionEntity) {
            return SPECTRAL_HALF_HEART_TEXTURE;
        }
        return sprite;
    }

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$renderSpectralSpyglassMiscOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.options.getPerspective().isFirstPerson() && ProjectionSpyglassItem.isInUseBy(this.client.player)) {
            float f = tickCounter.getDynamicDeltaTicks();
            this.spyglassScale = MathHelper.lerp(0.5F * f, this.spyglassScale, 1.125F);
            this.virtualAdditions$renderSpectralSpyglassOverlay(context, this.spyglassScale);
            ci.cancel();
        }
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getCameraPlayerFromProjection(CallbackInfoReturnable<PlayerEntity> cir) {
        if (this.client.getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity && ProjectionSpyglassItem.isInUseBy(this.client.player)) cir.setReturnValue(playerProjectionEntity.getPlayer());
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$renderSpectralSpyglassMiscOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.options.getPerspective().isFirstPerson() && this.client.cameraEntity instanceof PlayerProjectionEntity) {
            float f = tickCounter.getDynamicDeltaTicks();
            this.spyglassScale = MathHelper.lerp(0.5F * f, this.spyglassScale, 1.125F);
            this.virtualAdditions$renderSpectralSpyglassOverlay(context, this.spyglassScale);
            ci.cancel();
        }
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getCameraPlayerFromProjection(CallbackInfoReturnable<PlayerEntity> cir) {
        if (this.client.getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity) cir.setReturnValue(playerProjectionEntity.getPlayer());
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ItemInHandLayer.class)
public class HeldItemFeatureRendererMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ArmedModel> {
    @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    void virualAdditions$renderHalberdInUse(S entityState, ItemStackRenderState itemRenderState, ItemStack itemStack, HumanoidArm arm, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, CallbackInfo ci) {
        int flip = arm == HumanoidArm.LEFT ? -1 : 1;
        boolean bl = Boolean.TRUE.equals(entityState.getData(VARenderers.IS_HOLDING_HALBERD));
        boolean bl1 = bl && entityState instanceof HumanoidRenderState bipedState && bipedState.isUsingItem && Objects.nonNull(bipedState.getData(VARenderers.HALBERD_READINESS));
        boolean bl2 = bl && Objects.nonNull(entityState.getData(VARenderers.TICKS_SINCE_HALBERD_USED)) && entityState.getData(VARenderers.TICKS_SINCE_HALBERD_USED) < 12.0F;
        if (bl1 || bl2) {
            matrixStack.mulPose(Axis.ZP.rotationDegrees(-80.0F * flip));
            matrixStack.mulPose(Axis.XP.rotationDegrees(-34.0F));
            matrixStack.translate(-0.05 * flip, 0.0, 0.05);
        }
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ModelWithArms> {
    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    void virualAdditions$renderHalberdInUse(S entityState, ItemRenderState itemRenderState, ItemStack itemStack, Arm arm, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, CallbackInfo ci) {
        int flip = arm == Arm.LEFT ? -1 : 1;
        boolean bl = Boolean.TRUE.equals(entityState.getData(VARenderers.IS_HOLDING_HALBERD));
        boolean bl1 = bl && entityState instanceof BipedEntityRenderState bipedState && bipedState.isUsingItem && Objects.nonNull(bipedState.getData(VARenderers.HALBERD_READINESS));
        boolean bl2 = bl && Objects.nonNull(entityState.getData(VARenderers.TICKS_SINCE_HALBERD_USED)) && entityState.getData(VARenderers.TICKS_SINCE_HALBERD_USED) < 12.0F;
        if (bl1 || bl2) {
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-80.0F * flip));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-34.0F));
            matrixStack.translate(-0.05 * flip, 0.0, 0.05);
        }
    }
}

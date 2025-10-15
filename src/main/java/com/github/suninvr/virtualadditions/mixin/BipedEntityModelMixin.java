package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends BipedEntityRenderState> extends EntityModel {
    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    protected BipedEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$positionRightArmForHalberd(T state, CallbackInfo ci) {
        if (state.isUsingItem && Boolean.TRUE.equals(state.getData(VARenderers.IS_HOLDING_HALBERD)) && Objects.nonNull(state.getData(VARenderers.HALBERD_READINESS))) {
            float f = state.getData(VARenderers.HALBERD_READINESS);
            float ef = (float) Math.sin(Math.PI * (f / 2));
            float eg = 1.0F - ef;
            this.root.yaw = (float) ((ef * -55) * Math.PI / 180.0F);
            this.head.yaw = (state.relativeHeadYaw + (ef * 55)) * (float) (Math.PI / 180.0);
            this.leftArm.pitch = (float) (-80.0F * (Math.PI / 180.0F));
            this.leftArm.yaw = (float) (ef * -40.0F * (Math.PI / 180.0F));
            this.leftArm.roll = (float) (eg * -20.0F * (Math.PI / 180.0F));
            this.rightArm.pitch = (float) ( (eg*40 + -95.0F) * (Math.PI / 180.0F));
            this.rightArm.yaw = (float) ((-20.0F + -40.0F*eg) * (Math.PI / 180.0F));
            this.rightArm.roll = (float) ((30.0F + 30.0 * ef) * (Math.PI / 180.0F));
            ci.cancel();
        }
    }
    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$positionLeftArmForHalberd(T state, CallbackInfo ci) {
        if (state.isUsingItem && Boolean.TRUE.equals(state.getData(VARenderers.IS_HOLDING_HALBERD)) && Objects.nonNull(state.getData(VARenderers.HALBERD_READINESS))) {
            float f = state.getData(VARenderers.HALBERD_READINESS);
            float ef = (float) Math.sin(Math.PI * (f / 2));
            float eg = 1.0F - ef;
            this.root.yaw = (float) ((ef * 55) * Math.PI / 180.0F);
            this.head.yaw = (state.relativeHeadYaw + (ef * -55)) * (float) (Math.PI / 180.0);
            this.rightArm.pitch = (float) (-80.0F * (Math.PI / 180.0F));
            this.rightArm.yaw = (float) (ef * 40.0F * (Math.PI / 180.0F));
            this.rightArm.roll = (float) (eg * 20.0F * (Math.PI / 180.0F));
            this.leftArm.pitch = (float) ( (eg*40 + -95.0F) * (Math.PI / 180.0F));
            this.leftArm.yaw = (float) ((20.0F + 40.0F*eg) * (Math.PI / 180.0F));
            this.leftArm.roll = (float) ((-30.0F + -30.0 * ef) * (Math.PI / 180.0F));
            ci.cancel();
        }
    }
}

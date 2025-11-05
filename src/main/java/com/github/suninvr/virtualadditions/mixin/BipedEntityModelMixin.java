package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(HumanoidModel.class)
public abstract class BipedEntityModelMixin<T extends HumanoidRenderState> extends EntityModel {
    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    protected BipedEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$positionRightArmForHalberd(T state, CallbackInfo ci) {
        if (state.isUsingItem && HumanoidArm.LEFT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN))) ci.cancel();
        if (state.isUsingItem && HumanoidArm.RIGHT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN)) && Objects.nonNull(state.getData(VARenderers.HALBERD_READINESS))) {
            float f = state.getData(VARenderers.HALBERD_READINESS);
            float ef = (float) Math.sin(Math.PI * (f / 2));
            this.root.yRot = (float) ((ef * 55) * Math.PI / 180.0F);
            this.head.yRot = (state.yRot + (ef * -55)) * (float) (Math.PI / 180.0);
            this.rightArm.xRot = (float) (-80.0F * (Math.PI / 180.0F));
            this.rightArm.yRot = (float) (ef * 40.0F * (Math.PI / 180.0F));
            this.leftArm.xRot = (float) ( (-80.0F) * (Math.PI / 180.0F));
            this.leftArm.yRot = (float) ((20.0F + 20.0F * ef) * (Math.PI / 180.0F));
            ci.cancel();
        }
        try {
            float f = state.getData(VARenderers.TICKS_SINCE_HALBERD_USED);
            if (f <= 12 && HumanoidArm.RIGHT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN))) {
                float df = Math.min(f*20, 90);
                float dg = df / 90.0F;
                this.root.yRot = (float) ((55 - df * 1.3333F) * (Math.PI/180.0F));
                this.head.yRot = (state.yRot + (df * 1.3333F - 55)) * (float) (Math.PI / 180.0);

                this.rightArm.xRot = (float) (-80.0F * (Math.PI / 180.0F));
                this.rightArm.yRot = (float) ((40.0F - 50.0F * dg) * (Math.PI / 180.0F));
                this.leftArm.xRot = (float) ( (-80.0F) * (Math.PI / 180.0F));
                this.leftArm.yRot = (float) ((20.0F) * (Math.PI / 180.0F));
                ci.cancel();
            }
        } catch (NullPointerException ignored) {

        }
    }
    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$positionLeftArmForHalberd(T state, CallbackInfo ci) {
        if (state.isUsingItem && HumanoidArm.RIGHT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN))) ci.cancel();
        if (state.isUsingItem && HumanoidArm.LEFT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN)) && Objects.nonNull(state.getData(VARenderers.HALBERD_READINESS))) {
            float f = state.getData(VARenderers.HALBERD_READINESS);
            float ef = (float) Math.sin(Math.PI * (f / 2));
            this.root.yRot = (float) ((ef * -55) * Math.PI / 180.0F);
            this.head.yRot = (state.yRot + (ef * 55)) * (float) (Math.PI / 180.0);
            this.leftArm.xRot = (float) (-80.0F * (Math.PI / 180.0F));
            this.leftArm.yRot = (float) (ef * -40.0F * (Math.PI / 180.0F));
            this.rightArm.xRot = (float) ( (-80.0F) * (Math.PI / 180.0F));
            this.rightArm.yRot = (float) ((-20.0F - 20.0F*ef) * (Math.PI / 180.0F));
            ci.cancel();
        }
        try {
            float f = state.getData(VARenderers.TICKS_SINCE_HALBERD_USED);
            if (f <= 12 && HumanoidArm.LEFT.equals(state.getData(VARenderers.HOLDING_HALBERD_IN))) {
                float df = Math.min(f*20, 90);
                float dg = df / 90.0F;
                this.root.yRot = (float) ((df * 1.3333F - 55) * (Math.PI/180.0F));
                this.head.yRot = (state.yRot + (55 - df * 1.3333F)) * (float) (Math.PI / 180.0);

                this.leftArm.xRot = (float) (-80.0F * (Math.PI / 180.0F));
                this.leftArm.yRot = (float) ((-40.0F + 50.0F * dg) * (Math.PI / 180.0F));
                this.rightArm.xRot = (float) ((-80.0F) * (Math.PI / 180.0F));
                this.rightArm.yRot = (float) ((-20.0F) * (Math.PI / 180.0F));

                //this.rightArm.pitch = (float) (-80.0F * (Math.PI / 180.0F));
                //this.rightArm.yaw = (float) ((40.0F - 50.0F * dg) * (Math.PI / 180.0F));
                //this.leftArm.pitch = (float) ( (-80.0F) * (Math.PI / 180.0F));
                //this.leftArm.yaw = (float) ((20.0F) * (Math.PI / 180.0F));
                ci.cancel();
            }
        } catch (NullPointerException ignored) {

        }
    }
}

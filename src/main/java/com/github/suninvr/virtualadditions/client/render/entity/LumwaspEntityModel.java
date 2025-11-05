package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class LumwaspEntityModel<T extends LumwaspEntity> extends EntityModel<LumwaspEntityRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart thorax;
    private final ModelPart abdomen;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart left_mandible;
    private final ModelPart right_mandible;
    private final ModelPart front_left_leg;
    private final ModelPart middle_left_leg;
    private final ModelPart back_left_leg;
    private final ModelPart front_right_leg;
    private final ModelPart middle_right_leg;
    private final ModelPart back_right_leg;

    public LumwaspEntityModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.thorax = body.getChild("thorax_r1");
        this.abdomen = body.getChild("abdomen_r1");
        this.left_wing = body.getChild("wing_l_r1");
        this.right_wing = body.getChild("wing_r_r1");
        this.left_mandible = head.getChild("mandible_l_r1");
        this.right_mandible = head.getChild("mandible_r_r1");
        this.front_left_leg = body.getChild("leg_lf_r1");
        this.middle_left_leg = body.getChild("leg_lm_r1");
        this.back_left_leg = body.getChild("leg_lb_r1");
        this.front_right_leg = body.getChild("leg_rf_r1");
        this.middle_right_leg = body.getChild("leg_rm_r1");
        this.back_right_leg = body.getChild("leg_rb_r1");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -4.0F));

        PartDefinition leg_rb_r1 = body.addOrReplaceChild("leg_rb_r1", CubeListBuilder.create().texOffs(28, 26).addBox(-13.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 5.0F, -0.3842F, 1.0012F, -0.4476F));

        PartDefinition leg_rm_r1 = body.addOrReplaceChild("leg_rm_r1", CubeListBuilder.create().texOffs(36, 4).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 2.0F, -0.2145F, 0.5293F, -0.4074F));

        PartDefinition leg_rf_r1 = body.addOrReplaceChild("leg_rf_r1", CubeListBuilder.create().texOffs(36, 8).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, -1.0F, 0.1582F, -0.4084F, -0.3819F));

        PartDefinition leg_lb_r1 = body.addOrReplaceChild("leg_lb_r1", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 5.0F, -0.3842F, -1.0012F, 0.4476F));

        PartDefinition leg_lm_r1 = body.addOrReplaceChild("leg_lm_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 2.0F, -0.2145F, -0.5293F, 0.4074F));

        PartDefinition leg_lf_r1 = body.addOrReplaceChild("leg_lf_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, -1.0F, 0.1582F, 0.4084F, 0.3819F));

        PartDefinition wing_r_r1 = body.addOrReplaceChild("wing_r_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -1.0F, 4.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -2.0F, -1.0F, 0.0873F, 0.1745F, -1.3963F));

        PartDefinition wing_l_r1 = body.addOrReplaceChild("wing_l_r1", CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, 0.0F, -1.0F, 4.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, -1.0F, 0.0873F, -0.1745F, 1.3963F));

        PartDefinition abdomen_r1 = body.addOrReplaceChild("abdomen_r1", CubeListBuilder.create().texOffs(28, 12).addBox(-5.0F, -7.0F, -5.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 3.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition thorax_r1 = body.addOrReplaceChild("thorax_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -5.0F, -1.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 6.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 32).addBox(-5.0F, -3.0F, -5.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition mandible_r_r1 = head.addOrReplaceChild("mandible_r_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -5.0F, 0.2317F, 0.3405F, 0.0786F));

        PartDefinition mandible_l_r1 = head.addOrReplaceChild("mandible_l_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -5.0F, 2.9099F, 0.3405F, 3.063F));

        PartDefinition antenna_r_r1 = head.addOrReplaceChild("antenna_r_r1", CubeListBuilder.create().texOffs(34, 34).addBox(0.0F, -3.0F, -10.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -2.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition antenna_l_r1 = head.addOrReplaceChild("antenna_l_r1", CubeListBuilder.create().texOffs(34, 34).addBox(0.0F, -3.0F, -10.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, -2.0F, 0.0F, -0.3491F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(LumwaspEntityRenderState state) {
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        boolean inAir = state.inAir;
        float limbAngle = state.walkAnimationPos * 0.6662F;
        float limbDistance = state.walkAnimationSpeed;

        float u = (float) (Math.sin(animationProgress / 19) / 24);
        float v = (float) (Math.sin(animationProgress / 16) / 12);
        float w = (float) (Math.sin(animationProgress / 22) / 24);
        float x = (float) (Math.sin(animationProgress / 28) / 20);
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch  * 0.017453292F;
        this.thorax.yRot = u;
        if (inAir) {
            this.left_wing.xRot = (float) (1.0873F + Math.sin(animationProgress * 1.5) * 0.3);
            this.left_wing.yRot = -0.1745F;
            this.left_wing.zRot = 0.8963F;
            this.right_wing.xRot = (float) (1.0873F + Math.sin(animationProgress * 1.5) * 0.3);
            this.right_wing.yRot = 0.1745F;
            this.right_wing.zRot = -0.8963F;
            this.thorax.xRot = v;
            this.front_left_leg.yRot = -0.6084F + w;
            this.front_left_leg.zRot = 1.2819F + x;
            this.front_right_leg.yRot = 0.6084F + w;
            this.front_right_leg.zRot = -1.2819F + x;
            this.middle_left_leg.yRot = -0.8584F + w;
            this.middle_left_leg.zRot = 0.6819F + x;
            this.middle_right_leg.yRot = 0.8584F + w;
            this.middle_right_leg.zRot = -0.6819F + x;
            this.back_left_leg.yRot = -1.0012F + w;
            this.back_left_leg.zRot = 0.6819F + x;
            this.back_right_leg.yRot = 1.0012F + w;
            this.back_right_leg.zRot = -0.6819F + x;
        } else {
            this.left_wing.xRot = 0.0873F;
            this.left_wing.yRot = -0.1745F;
            this.left_wing.zRot = 1.3963F;
            this.right_wing.xRot = 0.0873F;
            this.right_wing.yRot = 0.1745F;
            this.right_wing.zRot = -1.3963F;
            this.thorax.xRot = v;

            this.front_left_leg.yRot = 0.4084F;
            this.front_right_leg.yRot = -0.4084F;
            this.middle_left_leg.yRot = -0.5293F;
            this.middle_right_leg.yRot = 0.5293F;
            this.back_left_leg.yRot = -1.0012F;
            this.back_right_leg.yRot = 1.0012F;
            this.front_left_leg.zRot = 0.3819F;
            this.front_right_leg.zRot = -0.3819F;
            this.middle_left_leg.zRot = 0.4074F;
            this.middle_right_leg.zRot = -0.4074F;
            this.back_left_leg.zRot = 0.4476F;
            this.back_right_leg.zRot = -0.4476F;

            float i = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
            float j = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbDistance;
            float l = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbDistance;
            float m = Math.abs(Mth.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
            float n = Math.abs(Mth.sin(limbAngle * 0.6662F + 3.1415927F) * 0.4F) * limbDistance;
            float p = Math.abs(Mth.sin(limbAngle * 0.6662F + 4.712389F) * 0.4F) * limbDistance;

            this.front_left_leg.yRot -= p;
            this.front_right_leg.yRot += p;
            this.middle_left_leg.yRot -= j;
            this.middle_right_leg.yRot += j;
            this.back_left_leg.yRot -= i;
            this.back_right_leg.yRot += i;
            this.front_left_leg.zRot -= l;
            this.front_right_leg.zRot += l;
            this.middle_left_leg.zRot -= n;
            this.middle_right_leg.zRot += n;
            this.back_left_leg.zRot -= m;
            this.back_right_leg.zRot += m;
        }
    }
}


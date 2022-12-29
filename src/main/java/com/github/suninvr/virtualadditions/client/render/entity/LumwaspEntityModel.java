package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class LumwaspEntityModel<T extends LumwaspEntity> extends EntityModel<LumwaspEntity> {
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
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.thorax = body.getChild("abdomen_r1");
        this.abdomen = body.getChild("thorax_r1");
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
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 18.0F, -4.0F));

        ModelPartData leg_rb_r1 = body.addChild("leg_rb_r1", ModelPartBuilder.create().uv(28, 26).cuboid(-13.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, 5.0F, -0.3842F, 1.0012F, -0.4476F));

        ModelPartData leg_rm_r1 = body.addChild("leg_rm_r1", ModelPartBuilder.create().uv(36, 4).cuboid(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, 2.0F, -0.2145F, 0.5293F, -0.4074F));

        ModelPartData leg_rf_r1 = body.addChild("leg_rf_r1", ModelPartBuilder.create().uv(36, 8).cuboid(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, -1.0F, 0.1582F, -0.4084F, -0.3819F));

        ModelPartData leg_lb_r1 = body.addChild("leg_lb_r1", ModelPartBuilder.create().uv(36, 0).cuboid(-1.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 2.0F, 5.0F, -0.3842F, -1.0012F, 0.4476F));

        ModelPartData leg_lm_r1 = body.addChild("leg_lm_r1", ModelPartBuilder.create().uv(0, 38).cuboid(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 2.0F, 2.0F, -0.2145F, -0.5293F, 0.4074F));

        ModelPartData leg_lf_r1 = body.addChild("leg_lf_r1", ModelPartBuilder.create().uv(0, 42).cuboid(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 2.0F, -1.0F, 0.1582F, 0.4084F, 0.3819F));

        ModelPartData wing_r_r1 = body.addChild("wing_r_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -1.0F, 4.0F, 0.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.0F, -1.0F, 0.0873F, 0.1745F, -1.3963F));

        ModelPartData wing_l_r1 = body.addChild("wing_l_r1", ModelPartBuilder.create().uv(8, 0).cuboid(0.0F, 0.0F, -1.0F, 4.0F, 0.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.0F, -1.0F, 0.0873F, -0.1745F, 1.3963F));

        ModelPartData thorax_r1 = body.addChild("thorax_r1", ModelPartBuilder.create().uv(28, 12).cuboid(-5.0F, -7.0F, -5.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 3.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData abdomen_r1 = body.addChild("abdomen_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-4.0F, -5.0F, -1.0F, 8.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 6.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(34, 32).cuboid(-5.0F, -3.0F, -5.0F, 8.0F, 6.0F, 6.0F, new Dilation(0.0F))
                .uv(34, 34).cuboid(1.0F, -6.0F, -12.0F, 0.0F, 3.0F, 10.0F, new Dilation(0.0F))
                .uv(34, 34).cuboid(-3.0F, -6.0F, -12.0F, 0.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -4.0F));

        ModelPartData mandible_r_r1 = head.addChild("mandible_r_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 3.5F, -4.5F, 0.0F, 0.0F, -0.4363F));

        ModelPartData mandible_l_r1 = head.addChild("mandible_l_r1", ModelPartBuilder.create().uv(0, 6).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 3.5F, -4.5F, 0.0F, 0.0F, 0.4363F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(LumwaspEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //0.0873F, 0.1745F, -1.3963F
        float u = (float) (Math.sin(animationProgress / 19) / 24);
        float v = (float) (Math.sin(animationProgress / 16) / 12);
        float w = (float) (Math.sin(animationProgress / 22) / 24);
        float x = (float) (Math.sin(animationProgress / 28) / 20);
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch  * 0.017453292F;
        this.thorax.yaw = u;
        this.left_mandible.yaw = u;
        this.left_mandible.pitch = v;
        this.right_mandible.yaw = u;
        this.right_mandible.pitch = v;
        if (entity.isInAir()) {
            this.left_wing.pitch = (float) (1.0873F + Math.sin(animationProgress * 1.5) * 0.3);
            this.left_wing.yaw = -0.1745F;
            this.left_wing.roll = 0.8963F;
            this.right_wing.pitch = (float) (1.0873F + Math.sin(animationProgress * 1.5) * 0.3);
            this.right_wing.yaw = 0.1745F;
            this.right_wing.roll = -0.8963F;
            this.thorax.pitch = v - 0.6F;
            this.front_left_leg.yaw = -0.6084F + w;
            this.front_left_leg.roll = 1.2819F + x;
            this.front_right_leg.yaw = 0.6084F + w;
            this.front_right_leg.roll = -1.2819F + x;
            this.middle_left_leg.yaw = -0.8584F + w;
            this.middle_left_leg.roll = 0.6819F + x;
            this.middle_right_leg.yaw = 0.8584F + w;
            this.middle_right_leg.roll = -0.6819F + x;
            this.back_left_leg.yaw = -1.0012F + w;
            this.back_left_leg.roll = 0.6819F + x;
            this.back_right_leg.yaw = 1.0012F + w;
            this.back_right_leg.roll = -0.6819F + x;
        } else {
            this.left_wing.pitch = 0.0873F;
            this.left_wing.yaw = -0.1745F;
            this.left_wing.roll = 1.3963F;
            this.right_wing.pitch = 0.0873F;
            this.right_wing.yaw = 0.1745F;
            this.right_wing.roll = -1.3963F;
            this.thorax.pitch = v;
            
            this.front_left_leg.yaw = 0.4084F;
            this.front_right_leg.yaw = -0.4084F;
            this.middle_left_leg.yaw = -0.5293F;
            this.middle_right_leg.yaw = 0.5293F;
            this.back_left_leg.yaw = -1.0012F;
            this.back_right_leg.yaw = 1.0012F;
            this.front_left_leg.roll = 0.3819F;
            this.front_right_leg.roll = -0.3819F;
            this.middle_left_leg.roll = 0.4074F;
            this.middle_right_leg.roll = -0.4074F;
            this.back_left_leg.roll = 0.4476F;
            this.back_right_leg.roll = -0.4476F;
            
            float i = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
            float j = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbDistance;
            float l = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbDistance;
            float m = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
            float n = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 3.1415927F) * 0.4F) * limbDistance;
            float p = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 4.712389F) * 0.4F) * limbDistance;
            
            this.front_left_leg.yaw += -p;
            this.front_right_leg.yaw += p;
            this.middle_left_leg.yaw += -j;
            this.middle_right_leg.yaw += j;
            this.back_left_leg.yaw += -i;
            this.back_right_leg.yaw += i;
            this.front_left_leg.roll += -l;
            this.front_right_leg.roll += l;
            this.middle_left_leg.roll += -n;
            this.middle_right_leg.roll += n;
            this.back_left_leg.roll += -m;
            this.back_right_leg.roll += m;
        }
    }
}


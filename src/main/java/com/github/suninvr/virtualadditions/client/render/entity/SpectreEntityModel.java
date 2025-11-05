package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class SpectreEntityModel<T extends LivingEntity> extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;

    protected SpectreEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox("head", -3, -3, -3, 6, 6, 6, new CubeDeformation(0.0F))
                , PartPose.offset(0, 20, 0));
        return LayerDefinition.create(modelData, 32, 16);
    }

    @Override
    public void setupAnim(LivingEntityRenderState object) {
        super.setupAnim(object);
        float headYaw = object.yRot;
        float headPitch = object.xRot;
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch  * 0.017453292F;
    }
}


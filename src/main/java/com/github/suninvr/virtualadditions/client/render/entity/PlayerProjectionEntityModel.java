package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PlayerProjectionEntityModel extends EntityModel<PlayerProjectionEntityRenderState> {
    private final ModelPart head;
    private final ModelPart hat;

    protected PlayerProjectionEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild(PartNames.HEAD);
        this.hat = head.getChild(PartNames.HAT);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox("head", -4, -4, -4, 8, 8, 8, new CubeDeformation(0.0F)),
                PartPose.offset(0, 20, 0));

        PartDefinition hat = head.addOrReplaceChild(
                PartNames.HAT, CubeListBuilder.create()
                                .texOffs(32, 0)
                        .addBox(-4, -4, -4, 8, 8, 8, new CubeDeformation(0.0F).extend(0.5F)),
                PartPose.ZERO
        );
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(PlayerProjectionEntityRenderState state) {
        super.setupAnim(state);
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch  * 0.017453292F;
        this.hat.visible = state.hatVisible;
    }
}


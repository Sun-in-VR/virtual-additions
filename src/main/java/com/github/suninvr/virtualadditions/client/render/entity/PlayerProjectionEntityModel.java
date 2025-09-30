package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class PlayerProjectionEntityModel extends EntityModel<PlayerProjectionEntityRenderState> {
    private final ModelPart head;
    private final ModelPart hat;

    protected PlayerProjectionEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.hat = head.getChild(EntityModelPartNames.HAT);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid("head", -4, -4, -4, 8, 8, 8, new Dilation(0.0F)),
                ModelTransform.origin(0, 20, 0));

        ModelPartData hat = head.addChild(
                EntityModelPartNames.HAT, ModelPartBuilder.create()
                                .uv(32, 0)
                        .cuboid(-4, -4, -4, 8, 8, 8, new Dilation(0.0F).add(0.5F)),
                ModelTransform.NONE
        );
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PlayerProjectionEntityRenderState state) {
        super.setAngles(state);
        float headYaw = state.relativeHeadYaw;
        float headPitch = state.pitch;
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch  * 0.017453292F;
    }
}


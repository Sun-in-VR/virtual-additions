package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class SpectreEntityModel<T extends SpectreEntity> extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;

    protected SpectreEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid("head", -3, -3, -3, 6, 6, 6, new Dilation(0.0F))
                , ModelTransform.origin(0, 18, 0));
        return TexturedModelData.of(modelData, 32, 16);
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {
        super.setAngles(state);
        float headYaw = state.relativeHeadYaw;
        float headPitch = state.pitch;
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch  * 0.017453292F;
    }
}


package com.github.suninvr.virtualadditions.client.render.entity;

import com.github.suninvr.virtualadditions.entity.LyftEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class LyftEntityModel<T extends LyftEntity> extends EntityModel<LivingEntityRenderState> {
    private final ModelPart root;
    private final ModelPart outer;
    private final ModelPart inner;
    public LyftEntityModel(ModelPart root) {
        this.root = root;
        this.outer = root.getChild("outer");
        this.inner = root.getChild("inner");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData outer = modelPartData.addChild("outer", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -10.0F, -8.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData inner = modelPartData.addChild("inner", ModelPartBuilder.create().uv(0, 24).cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {

    }
}


package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.block.entity.CustomBedBlockEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class CustomBedBlockEntityRenderer implements BlockEntityRenderer<CustomBedBlockEntity> {
    public static final ModelProvider HEAD_MODEL_PROVIDER = new ModelProvider(true);
    public static final ModelProvider FOOT_MODEL_PROVIDER = new ModelProvider(false);
    private final ModelPart bedHead;
    private final ModelPart bedFoot;

    public CustomBedBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.bedHead = ctx.getLayerModelPart(EntityModelLayers.BED_HEAD);
        this.bedFoot = ctx.getLayerModelPart(EntityModelLayers.BED_FOOT);
    }

    public static TexturedModelData getHeadTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 6.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(50, 6).cuboid(0.0f, 6.0f, 0.0f, 3.0f, 3.0f, 3.0f), ModelTransform.rotation(1.5707964f, 0.0f, 1.5707964f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(50, 18).cuboid(-16.0f, 6.0f, 0.0f, 3.0f, 3.0f, 3.0f), ModelTransform.rotation(1.5707964f, 0.0f, (float)Math.PI));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getFootTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 22).cuboid(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 6.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(50, 0).cuboid(0.0f, 6.0f, -16.0f, 3.0f, 3.0f, 3.0f), ModelTransform.rotation(1.5707964f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(50, 12).cuboid(-16.0f, 6.0f, -16.0f, 3.0f, 3.0f, 3.0f), ModelTransform.rotation(1.5707964f, 0.0f, 4.712389f));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(CustomBedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        SpriteIdentifier spriteIdentifier = bedBlockEntity.getExtendedColor().getBedTexture();
        World world2 = bedBlockEntity.getWorld();
        if (world2 != null) {
            BlockState blockState = bedBlockEntity.getCachedState();
            DoubleBlockProperties.PropertySource<BedBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(BlockEntityType.BED, BedBlock::getBedPart, BedBlock::getOppositePartDirection, ChestBlock.FACING, blockState, world2, bedBlockEntity.getPos(), (world, pos) -> false);
            int k = ((Int2IntFunction)propertySource.apply(new LightmapCoordinatesRetriever())).get(i);
            this.renderPart(matrixStack, vertexConsumerProvider, blockState.get(BedBlock.PART) == BedPart.HEAD ? this.bedHead : this.bedFoot, blockState.get(BedBlock.FACING), spriteIdentifier, k, j, false);
        } else {
            this.renderPart(matrixStack, vertexConsumerProvider, this.bedHead, Direction.SOUTH, spriteIdentifier, i, j, false);
            this.renderPart(matrixStack, vertexConsumerProvider, this.bedFoot, Direction.SOUTH, spriteIdentifier, i, j, true);
        }
    }

    private void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ModelPart part, Direction direction, SpriteIdentifier sprite, int light, int overlay, boolean isFoot) {
        matrices.push();
        matrices.translate(0.0f, 0.5625f, isFoot ? -1.0f : 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f + direction.asRotation()));
        matrices.translate(-0.5f, -0.5f, -0.5f);
        VertexConsumer vertexConsumer = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        part.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }

    private static class ModelProvider implements EntityModelLayerRegistry.TexturedModelDataProvider {
        private final boolean head;
        protected ModelProvider(boolean head) {
            this.head = head;
        }

        @Override
        public TexturedModelData createModelData() {
            return this.head ? CustomBedBlockEntityRenderer.getHeadTexturedModelData() : CustomBedBlockEntityRenderer.getFootTexturedModelData();
        }
    }
}

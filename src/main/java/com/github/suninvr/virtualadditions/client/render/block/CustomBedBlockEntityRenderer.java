package com.github.suninvr.virtualadditions.client.render.block;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomBedBlockEntityRenderer extends BedBlockEntityRenderer {
    public static final ModelProvider HEAD_MODEL_PROVIDER = new ModelProvider(true);
    public static final ModelProvider FOOT_MODEL_PROVIDER = new ModelProvider(false);
    private final ModelPart bedHead;
    private final ModelPart bedFoot;

    public CustomBedBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        this.bedHead = ctx.getLayerModelPart(EntityModelLayers.BED_HEAD);
        this.bedFoot = ctx.getLayerModelPart(EntityModelLayers.BED_FOOT);
    }

    @Override
    public void render(BedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, int i, int j, Vec3d vec3d, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, OrderedRenderCommandQueue orderedRenderCommandQueue) {

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

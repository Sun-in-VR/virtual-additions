package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.block.CustomBedBlock;
import com.github.suninvr.virtualadditions.block.CustomShulkerBoxBlock;
import com.github.suninvr.virtualadditions.block.entity.CustomBedBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.CustomShulkerBoxBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class CustomShulkerBoxItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final CustomShulkerBoxBlockEntity renderer = new CustomShulkerBoxBlockEntity(ExtendedDyeColor.CHARTREUSE, BlockPos.ORIGIN, VABlocks.CHARTREUSE_BED.getDefaultState());

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof CustomShulkerBoxBlock block) {
            renderer.setExtendedDyeColor(block.getExtendedDyeColor());
        }
        BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
        dispatcher.renderEntity(renderer, matrices, vertexConsumers, light, overlay);
    }
}

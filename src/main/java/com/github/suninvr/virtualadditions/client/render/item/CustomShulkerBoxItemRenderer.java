package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.block.CustomShulkerBoxBlock;
import com.github.suninvr.virtualadditions.block.entity.CustomShulkerBoxBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.BlockPos;

public class CustomShulkerBoxItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final CustomShulkerBoxBlockEntity CHARTREUSE = new CustomShulkerBoxBlockEntity(VADyeColors.CHARTREUSE, BlockPos.ORIGIN, VABlocks.CHARTREUSE_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity MAROON = new CustomShulkerBoxBlockEntity(VADyeColors.MAROON, BlockPos.ORIGIN, VABlocks.MAROON_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity INDIGO = new CustomShulkerBoxBlockEntity(VADyeColors.INDIGO, BlockPos.ORIGIN, VABlocks.INDIGO_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity PLUM = new CustomShulkerBoxBlockEntity(VADyeColors.PLUM, BlockPos.ORIGIN, VABlocks.PLUM_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity VIRIDIAN = new CustomShulkerBoxBlockEntity(VADyeColors.VIRIDIAN, BlockPos.ORIGIN, VABlocks.VIRIDIAN_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity TAN = new CustomShulkerBoxBlockEntity(VADyeColors.TAN, BlockPos.ORIGIN, VABlocks.TAN_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity SINOPIA = new CustomShulkerBoxBlockEntity(VADyeColors.SINOPIA, BlockPos.ORIGIN, VABlocks.SINOPIA_SHULKER_BOX.getDefaultState());
    private static final CustomShulkerBoxBlockEntity LILAC = new CustomShulkerBoxBlockEntity(VADyeColors.LILAC, BlockPos.ORIGIN, VABlocks.LILAC_SHULKER_BOX.getDefaultState());

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof CustomShulkerBoxBlock) {
            BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
            BlockEntity renderer = getRenderer(stack);
            if (renderer != null) dispatcher.renderEntity(renderer, matrices, vertexConsumers, light, overlay);
        }
    }

    private BlockEntity getRenderer(ItemStack stack) {
        if (stack.isOf(VAItems.CHARTREUSE_SHULKER_BOX)) return CHARTREUSE;
        if (stack.isOf(VAItems.MAROON_SHULKER_BOX)) return MAROON;
        if (stack.isOf(VAItems.INDIGO_SHULKER_BOX)) return INDIGO;
        if (stack.isOf(VAItems.PLUM_SHULKER_BOX)) return PLUM;
        if (stack.isOf(VAItems.VIRIDIAN_SHULKER_BOX)) return VIRIDIAN;
        if (stack.isOf(VAItems.TAN_SHULKER_BOX)) return TAN;
        if (stack.isOf(VAItems.SINOPIA_SHULKER_BOX)) return SINOPIA;
        if (stack.isOf(VAItems.LILAC_SHULKER_BOX)) return LILAC;
        return CHARTREUSE;
    }
}

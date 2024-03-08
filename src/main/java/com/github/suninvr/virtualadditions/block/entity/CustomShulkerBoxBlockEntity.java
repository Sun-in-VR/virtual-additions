package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {

    public CustomShulkerBoxBlockEntity(DyeColor color, BlockPos pos, BlockState state) {
        super(color, pos, state);
    }

    public CustomShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_SHULKER_BOX;
    }


}

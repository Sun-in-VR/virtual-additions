package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlockEntity extends BedBlockEntity {

    public CustomBedBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, VADyeColors.CHARTREUSE);
    }

    public CustomBedBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(pos, state, color);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_BED;
    }
}

package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlockEntity extends BedBlockEntity {
    private final BlockEntityType<?> type;
    private ExtendedDyeColor extendedColor;

    public CustomBedBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, ExtendedDyeColor.CHARTREUSE);
    }

    public CustomBedBlockEntity(BlockPos pos, BlockState state, ExtendedDyeColor extendedColor) {
        super(pos, state);
        this.type = VABlockEntityType.CUSTOM_BED;
        this.extendedColor = extendedColor;
    }

    @Override
    public BlockEntityType<?> getType() {
        return this.type;
    }

    public ExtendedDyeColor getExtendedColor() {
        return this.extendedColor;
    }

    public void setColor(ExtendedDyeColor color) {
        this.extendedColor = color;
    }
}

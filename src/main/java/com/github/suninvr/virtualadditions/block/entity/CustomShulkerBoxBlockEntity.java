package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CustomShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {
    private ExtendedDyeColor color;

    public CustomShulkerBoxBlockEntity(ExtendedDyeColor color, BlockPos pos, BlockState state) {
        super(null, pos, state);
        this.color = color;
    }

    public CustomShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
        this.color = ExtendedDyeColor.CHARTREUSE;
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_SHULKER_BOX;
    }

    public ExtendedDyeColor getExtendedDyeColor() {
        return this.color;
    }

    public void setExtendedDyeColor(ExtendedDyeColor color) {
        this.color = color;
    }


}

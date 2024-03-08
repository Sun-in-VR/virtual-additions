package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomBannerBlockEntity extends BannerBlockEntity {
    public CustomBannerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public CustomBannerBlockEntity(BlockPos pos, BlockState state, DyeColor baseColor) {
        super(pos, state, baseColor);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_BANNER;
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomBannerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomWallBannerBlock extends WallBannerBlock {
    private final DyeColor color;

    public CustomWallBannerBlock(DyeColor dyeColor, Settings settings) {
        super(dyeColor, settings);
        this.color = dyeColor;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBannerBlockEntity(pos, state,color);
    }
}
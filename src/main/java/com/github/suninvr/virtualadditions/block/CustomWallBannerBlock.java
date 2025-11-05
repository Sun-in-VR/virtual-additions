package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomBannerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomWallBannerBlock extends WallBannerBlock {
    private final DyeColor color;

    public CustomWallBannerBlock(DyeColor dyeColor, Properties settings) {
        super(dyeColor, settings);
        this.color = dyeColor;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBannerBlockEntity(pos, state,color);
    }
}

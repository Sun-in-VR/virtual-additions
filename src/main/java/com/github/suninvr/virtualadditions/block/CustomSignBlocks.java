package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomHangingSignBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.CustomSignBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CustomSignBlocks {
    public static class CustomSignBlock extends SignBlock {
        public CustomSignBlock(Settings settings, WoodType woodType) {
            super(settings, woodType);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state);
        }
    }

    public static class CustomWallSignBlock extends WallSignBlock {
        public CustomWallSignBlock(Settings settings, WoodType woodType) {
            super(settings, woodType);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state);
        }
    }

    public static class CustomHangingSignBlock extends HangingSignBlock {
        public CustomHangingSignBlock(Settings settings, WoodType woodType) {
            super(settings, woodType);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state);
        }
    }

    public static class CustomWallHangingSignBlock extends WallHangingSignBlock {
        public CustomWallHangingSignBlock(Settings settings, WoodType woodType) {
            super(settings, woodType);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state);
        }
    }
}

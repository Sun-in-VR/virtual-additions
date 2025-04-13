package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomHangingSignBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.CustomSignBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomSignBlocks {
    public static class CustomSignBlock extends SignBlock {
        private final DyeColor defaultColor;

        public CustomSignBlock(Settings settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomSignBlock(Settings settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state, this.defaultColor);
        }
    }

    public static class CustomWallSignBlock extends WallSignBlock {
        private final DyeColor defaultColor;

        public CustomWallSignBlock(Settings settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomWallSignBlock(Settings settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state, this.defaultColor);
        }
    }

    public static class CustomHangingSignBlock extends HangingSignBlock {
        private final DyeColor defaultColor;

        public CustomHangingSignBlock(Settings settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomHangingSignBlock(Settings settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state, defaultColor);
        }
    }

    public static class CustomWallHangingSignBlock extends WallHangingSignBlock {
        private final DyeColor defaultColor;

        public CustomWallHangingSignBlock(Settings settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomWallHangingSignBlock(Settings settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state, defaultColor);
        }
    }
}

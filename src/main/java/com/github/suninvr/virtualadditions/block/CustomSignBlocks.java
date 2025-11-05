package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomHangingSignBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.CustomSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CustomSignBlocks {
    public static class CustomSignBlock extends StandingSignBlock {
        private final DyeColor defaultColor;

        public CustomSignBlock(Properties settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomSignBlock(Properties settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state, this.defaultColor);
        }
    }

    public static class CustomWallSignBlock extends WallSignBlock {
        private final DyeColor defaultColor;

        public CustomWallSignBlock(Properties settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomWallSignBlock(Properties settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CustomSignBlockEntity(pos, state, this.defaultColor);
        }
    }

    public static class CustomHangingSignBlock extends CeilingHangingSignBlock {
        private final DyeColor defaultColor;

        public CustomHangingSignBlock(Properties settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomHangingSignBlock(Properties settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state, defaultColor);
        }
    }

    public static class CustomWallHangingSignBlock extends WallHangingSignBlock {
        private final DyeColor defaultColor;

        public CustomWallHangingSignBlock(Properties settings, WoodType woodType) {
            super(woodType, settings);
            this.defaultColor = DyeColor.BLACK;
        }

        public CustomWallHangingSignBlock(Properties settings, WoodType woodType, DyeColor defaultColor) {
            super(woodType, settings);
            this.defaultColor = defaultColor;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CustomHangingSignBlockEntity(pos, state, defaultColor);
        }
    }
}

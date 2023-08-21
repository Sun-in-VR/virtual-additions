package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class CustomHangingSignBlockEntity extends HangingSignBlockEntity {
    public CustomHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntities.CUSTOM_HANGING_SIGN_BLOCK_ENTITY;
    }
}

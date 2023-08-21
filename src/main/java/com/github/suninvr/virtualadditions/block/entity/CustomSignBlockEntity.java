package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class CustomSignBlockEntity extends SignBlockEntity {
    public CustomSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntities.CUSTOM_SIGN_BLOCK_ENTITY;
    }
}

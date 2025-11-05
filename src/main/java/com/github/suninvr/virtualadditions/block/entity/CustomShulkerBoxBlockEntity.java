package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {

    public CustomShulkerBoxBlockEntity(DyeColor color, BlockPos pos, BlockState state) {
        super(color, pos, state);
    }

    public CustomShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_SHULKER_BOX;
    }


}

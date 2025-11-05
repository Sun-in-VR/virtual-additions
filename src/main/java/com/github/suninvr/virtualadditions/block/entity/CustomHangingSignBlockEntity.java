package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;

public class CustomHangingSignBlockEntity extends HangingSignBlockEntity {
    public CustomHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public CustomHangingSignBlockEntity(BlockPos blockPos, BlockState blockState, DyeColor defaultColor) {
        super(blockPos, blockState);
        this.frontText = createText(defaultColor);
        this.backText = createText(defaultColor);
    }

    protected SignText createText(DyeColor color) {
        return new SignText().setColor(color);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_HANGING_SIGN;
    }
}

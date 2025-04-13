package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomSignBlockEntity extends SignBlockEntity {
    public CustomSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public CustomSignBlockEntity(BlockPos pos, BlockState state, DyeColor defaultColor) {
        super(pos, state);
        this.frontText = createText(defaultColor);
        this.backText = createText(defaultColor);
    }

    protected SignText createText(DyeColor color) {
        return new SignText().withColor(color);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VABlockEntityType.CUSTOM_SIGN;
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomBedBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlock extends BedBlock {
    private final ExtendedDyeColor color;

    public CustomBedBlock(ExtendedDyeColor color, Settings settings) {
        super(DyeColor.WHITE, settings);
        this.color = color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBedBlockEntity(pos, state, this.color);
    }

    public SpriteIdentifier getTexture() {
        return this.color.getBedTexture();
    }

    public ExtendedDyeColor getExtendedColor() {
        return this.color;
    }
}

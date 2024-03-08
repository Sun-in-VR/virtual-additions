package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomBedBlockEntity;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlock extends BedBlock {
    DyeColor color;

    public CustomBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
        this.color = color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBedBlockEntity(pos, state, this.color);
    }

    public SpriteIdentifier getTexture() {
        return VADyeColors.getBedTexture(this.color);
    }
}

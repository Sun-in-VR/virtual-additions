package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class CottonCropBlock extends CropBlock {
    public static final MapCodec<CottonCropBlock> CODEC = simpleCodec(CottonCropBlock::new);
    public CottonCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return VAItems.COTTON_SEEDS;
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class CottonCropBlock extends CropBlock {
    public static final MapCodec<CottonCropBlock> CODEC = createCodec(CottonCropBlock::new);
    public CottonCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CropBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return VAItems.COTTON_SEEDS;
    }
}

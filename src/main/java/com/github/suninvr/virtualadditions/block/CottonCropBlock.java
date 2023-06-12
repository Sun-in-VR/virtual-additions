package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class CottonCropBlock extends CropBlock {
    public CottonCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return VAItems.COTTON_SEEDS;
    }
}

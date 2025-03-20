package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class TomatoCropBlock extends CropBlock {
    public static final MapCodec<TomatoCropBlock> CODEC = createCodec(TomatoCropBlock::new);
    public TomatoCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CropBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return VAItems.TOMATO_SEEDS;
    }
}

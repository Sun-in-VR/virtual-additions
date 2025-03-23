package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CabbageCropBlock extends CropBlock {
    public static final MapCodec<CabbageCropBlock> CODEC = createCodec(CabbageCropBlock::new);
    private static final VoxelShape[] SHAPES_BY_AGE = Block.createShapeArray(7, (age) -> Block.createColumnShape(16.0, 0.0, 2 + age));

    public CabbageCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CropBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return VAItems.CABBAGE_SEEDS;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }
}

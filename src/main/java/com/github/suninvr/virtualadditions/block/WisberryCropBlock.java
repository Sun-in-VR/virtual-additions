package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WisberryCropBlock extends CropBlock {
    private static final VoxelShape[] SHAPES_BY_AGE = Block.createShapeArray(7, (age) -> {
        return Block.createColumnShape(16.0, 0.0, (double)(2 + age));
    });

    public WisberryCropBlock(Settings settings) {
        super(settings);
    }

    protected ItemConvertible getSeedsItem() {
        return VAItems.WISBERRY_SEEDS;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }
}

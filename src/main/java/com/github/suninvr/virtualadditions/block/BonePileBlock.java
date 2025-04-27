package com.github.suninvr.virtualadditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BonePileBlock extends PlantBlock {
    public static final MapCodec<CactusFlowerBlock> CODEC = createCodec(CactusFlowerBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(14.0, 0.0, 10.0);

    public BonePileBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isSideSolid(world, pos, Direction.UP, SideShapeType.FULL);
    }
}

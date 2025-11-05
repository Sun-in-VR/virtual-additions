package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CabbageCropBlock extends CropBlock {
    public static final MapCodec<CabbageCropBlock> CODEC = simpleCodec(CabbageCropBlock::new);
    private static final VoxelShape[] SHAPES_BY_AGE = Block.boxes(7, (age) -> Block.column(16.0, 0.0, 2 + age));

    public CabbageCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return VAItems.CABBAGE_SEEDS;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }
}

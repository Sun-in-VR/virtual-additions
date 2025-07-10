package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WisdomBerryCropBlock extends CropBlock {
    public static final IntProvider experienceDropped = UniformIntProvider.create(0, 1);
    private static final VoxelShape[] SHAPES_BY_AGE = Block.createShapeArray(7, (age) -> Block.createColumnShape(16.0, 0.0, (double)(2 + age)));

    public WisdomBerryCropBlock(Settings settings) {
        super(settings);
    }

    protected ItemConvertible getSeedsItem() {
        return VAItems.WISDOM_BERRY_SEEDS;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }

    @Override
    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        if (state.get(Properties.AGE_7) == 7 & dropExperience) {
            this.dropExperienceWhenMined(world, pos, tool, experienceDropped);
        }
    }
}

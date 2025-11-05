package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WisdomBerryCropBlock extends CropBlock {
    public static final IntProvider experienceDropped = UniformInt.of(0, 1);
    private static final VoxelShape[] SHAPES_BY_AGE = Block.boxes(7, (age) -> Block.column(16.0, 0.0, (double)(2 + age)));

    public WisdomBerryCropBlock(Properties settings) {
        super(settings);
    }

    protected ItemLike getBaseSeedId() {
        return VAItems.WISDOM_BERRY_SEEDS;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        if (state.getValue(BlockStateProperties.AGE_7) == 7 & dropExperience) {
            this.tryDropExperience(world, pos, tool, experienceDropped);
        }
    }
}

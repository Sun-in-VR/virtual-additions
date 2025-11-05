package com.github.suninvr.virtualadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class WitheredSaplingBlock extends SaplingBlock {
    public WitheredSaplingBlock(TreeGrower generator, Properties settings) {
        super(generator, settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.NYLIUM) || floor.is(BlockTags.SOUL_FIRE_BASE_BLOCKS) || floor.is(Blocks.NETHERRACK) || super.mayPlaceOn(floor, world, pos);
    }
}

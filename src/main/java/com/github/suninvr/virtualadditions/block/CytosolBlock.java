package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CytosolBlock extends AbstractFilmBlock implements Fertilizable {
    public static final BooleanProperty GROWTHS = BooleanProperty.of("growths");

    public CytosolBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GROWTHS, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(GROWTHS));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        if (state.isOf(this)) return state.get(GROWTHS);
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        Direction.Axis axis = state.get(AXIS);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (!state.get(GROWTHS)) {
            world.setBlockState(pos, state.with(GROWTHS, true));
        } else {
            world.setBlockState(pos, VABlocks.ORGANELLE.getDefaultState().with(AXIS, state.get(AXIS)));
        }
    }
}

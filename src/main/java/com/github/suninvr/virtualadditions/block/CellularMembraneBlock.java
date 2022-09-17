package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.MembraneStatus;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CellularMembraneBlock extends Block implements Fertilizable {
    public static final IntProperty AGE = IntProperty.of("age", 0, 24);
    public static final EnumProperty<MembraneStatus> STATUS = EnumProperty.of("status", MembraneStatus.class);
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public CellularMembraneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(STATUS, MembraneStatus.STARTING).with(AXIS, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(AGE).add(STATUS).add(AXIS));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(STATUS) != MembraneStatus.STOPPED;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(6) == 0) grow(world, random, pos, state);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(STATUS) != MembraneStatus.STOPPED;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.get(STATUS) != MembraneStatus.STOPPED;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int sel = random.nextInt(10) + 1;
        BlockState filmState = switch (sel) {
            case 6, 7, 8, 9, 10 -> VABlocks.CYTOSOL.getDefaultState();
            case 2, 3, 4, 5 -> VABlocks.CYTOSOL.getDefaultState().with(CytosolBlock.GROWTHS, true);
            case 1 -> VABlocks.ORGANELLE.getDefaultState();
            default -> throw new IllegalStateException("Unexpected value: " + sel);
        };

        int age = state.get(AGE);
        boolean stopGrowing = random.nextInt(25 - age) == 0 && age > 12;
        if (!stopGrowing) {
            world.setBlockState(pos, filmState.with(AXIS, state.get(AXIS)));
            expand(world, pos, state);
        } else {
            boolean bl = true;
            BlockState[] neighbors = getNeighbors(world, pos, state);
            for (BlockState neighbor : neighbors) {
                if (!AbstractFilmBlock.isFilm(neighbor)) bl = false;
            }
            if (!bl) world.setBlockState(pos, state.with(STATUS, MembraneStatus.STOPPED));
            else world.setBlockState(pos, filmState.with(AXIS, state.get(AXIS)));
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (state.get(STATUS) != MembraneStatus.EXPANDING) return;
        BlockState[] neighbors = getNeighbors(world, pos, state);
        for (BlockState neighbor : neighbors) {
            if (AbstractFilmBlock.isFilm(neighbor)) return;
        }
        world.setBlockState(pos, state.with(STATUS, MembraneStatus.STOPPED));


    }

    protected BlockState[] getNeighbors(World world, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.get(AXIS);
        Direction.Axis horzontalAxis = Direction.Axis.X;
        Direction.Axis verticalAxis = Direction.Axis.Y;
        switch (axis) {
            case X -> horzontalAxis = Direction.Axis.Z;
            case Y -> verticalAxis = Direction.Axis.Z;
            case Z -> {}
        }
        return new BlockState[]{
                world.getBlockState(pos.offset(verticalAxis, 1)),
                world.getBlockState(pos.offset(verticalAxis, -1)),
                world.getBlockState(pos.offset(horzontalAxis, 1)),
                world.getBlockState(pos.offset(horzontalAxis, -1))
        };
    }

    private void expand(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.get(AGE) >= 24) return;
        BlockState newState = state.with(AGE, state.get(AGE) + 1).with(STATUS, MembraneStatus.EXPANDING);
        Direction.Axis axis = state.get(AXIS);
        Direction.Axis horzontalAxis = Direction.Axis.X;
        Direction.Axis verticalAxis = Direction.Axis.Y;
        switch (axis) {
            case X -> horzontalAxis = Direction.Axis.Z;
            case Y -> verticalAxis = Direction.Axis.Z;
            case Z -> {}
        }
        if (world.getBlockState( pos.offset(verticalAxis, 1) ).isAir()) world.setBlockState(pos.offset(verticalAxis, 1), newState);
        if (world.getBlockState( pos.offset(verticalAxis, -1) ).isAir()) world.setBlockState(pos.offset(verticalAxis, -1), newState);
        if (world.getBlockState( pos.offset(horzontalAxis, 1) ).isAir()) world.setBlockState(pos.offset(horzontalAxis, 1), newState);
        if (world.getBlockState( pos.offset(horzontalAxis, -1) ).isAir()) world.setBlockState(pos.offset(horzontalAxis, -1), newState);
    }
}

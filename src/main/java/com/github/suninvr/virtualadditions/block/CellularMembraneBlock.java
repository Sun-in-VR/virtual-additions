package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.MembraneStatus;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
    private record BlockStatePos(BlockState state, BlockPos pos){}

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
        if (state.get(STATUS) == MembraneStatus.DYING) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }
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

        int age = state.get(AGE);
        boolean stopGrowing = random.nextInt(25 - age) == 0 && age > 12;
        if (!stopGrowing) {
            if (state.get(STATUS) == MembraneStatus.STARTING) {
                expand(world, pos, state, VABlocks.NUCLEUS.getDefaultState());
                return;
            }
            expand(world, pos, state);
        } else {
            boolean bl = true;
            BlockStatePos[] neighbors = getNeighbors(world, pos, state);
            for (BlockStatePos neighbor : neighbors) {
                if (!AbstractFilmBlock.isFilm(neighbor.state())) bl = false;
            }
            if (!bl) world.setBlockState(pos, state.with(STATUS, MembraneStatus.STOPPED));
            else world.setBlockState(pos, getRandomFilmState(random).with(AXIS, state.get(AXIS)));
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (state.get(STATUS) == MembraneStatus.STARTING) return;
        BlockStatePos[] neighbors = getNeighbors(world, pos, state);
        for (BlockStatePos neighbor : neighbors) {
            if (AbstractFilmBlock.isFilm(neighbor.state())) return;
        }
        world.setBlockState(pos, state.with(STATUS, MembraneStatus.DYING));


    }

    protected BlockStatePos[] getNeighbors(World world, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.get(AXIS);
        Direction.Axis horzontalAxis = Direction.Axis.X;
        Direction.Axis verticalAxis = Direction.Axis.Y;
        switch (axis) {
            case X -> horzontalAxis = Direction.Axis.Z;
            case Y -> verticalAxis = Direction.Axis.Z;
            case Z -> {}
        }
        return new BlockStatePos[]{
                new BlockStatePos(world.getBlockState(pos.offset(verticalAxis, 1)), pos.offset(verticalAxis, 1)),
                new BlockStatePos(world.getBlockState(pos.offset(verticalAxis, -1)), pos.offset(verticalAxis, -1)),
                new BlockStatePos(world.getBlockState(pos.offset(horzontalAxis, 1)), pos.offset(horzontalAxis, 1)),
                new BlockStatePos(world.getBlockState(pos.offset(horzontalAxis, -1)), pos.offset(horzontalAxis, -1)),
        };
    }

    protected BlockState getRandomFilmState(Random random) {
        int sel = random.nextInt(10) + 1;
        return switch (sel) {
            case 6, 7, 8, 9, 10 -> VABlocks.CYTOSOL.getDefaultState();
            case 2, 3, 4, 5 -> VABlocks.CYTOSOL.getDefaultState().with(CytosolFilmBlock.GROWTHS, true);
            case 1 -> {
                int i = random.nextInt(35);
                yield (i == 0) ? VABlocks.NUCLEUS.getDefaultState() : VABlocks.ORGANELLE.getDefaultState();
            }
            default -> throw new IllegalStateException("Unexpected value: " + sel);
        };
    }

    private void expand(ServerWorld world, BlockPos pos, BlockState state) {
        expand(world, pos, state, getRandomFilmState(world.getRandom()));
    }

    private void expand(ServerWorld world, BlockPos pos, BlockState state, BlockState filmState) {
        if (state.get(AGE) >= 24) return;
        BlockState newState = state.with(AGE, state.get(AGE) + 1).with(STATUS, MembraneStatus.EXPANDING);
        boolean canExpand = true;
        BlockStatePos[] neighbors = getNeighbors(world, pos, state);
        for (BlockStatePos neighbor : neighbors) {
            if (!(neighbor.state().isAir())) {
                if (!(CytosolFilmBlock.isFilm(neighbor.state()) || neighbor.state().isOf(this))) canExpand = false;
            }
        }
        if (canExpand) {
            for (BlockStatePos neighbor : neighbors) {
                if (neighbor.state().isAir()) world.setBlockState(neighbor.pos(), newState);
            }
            world.setBlockState(pos, filmState.with(AXIS, state.get(AXIS)));
        } else {
            world.setBlockState(pos, state.with(STATUS, MembraneStatus.STOPPED));
        }
    }
}

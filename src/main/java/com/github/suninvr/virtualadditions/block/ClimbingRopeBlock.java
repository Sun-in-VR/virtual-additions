package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;

import java.util.Random;

public class ClimbingRopeBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HOPPER_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty END = BooleanProperty.of("end");
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape DOWN_SHAPE;

    public ClimbingRopeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.DOWN)
                .with(WATERLOGGED, false)
                .with(END, true)
        );
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> DOWN_SHAPE;
        };
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.canPlaceAt(world, pos)) {
            BlockPos belowPos = pos.down();
            BlockState belowState = world.getBlockState(belowPos);
            if (world.getBottomY() <= belowPos.getY() && (belowState.canReplace(new AutomaticItemPlacementContext(world, belowPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) && !belowState.isOf(Blocks.LAVA)) {
                BlockState newState = state.with(WATERLOGGED, world.getFluidState(belowPos).getFluid() == Fluids.WATER);
                world.setBlockState(belowPos, newState);
                world.setBlockState(pos, state.with(END, false));
                world.playSound(null, belowPos, VASoundEvents.BLOCK_ROPE_EXTEND, SoundCategory.BLOCKS, 0.33F, 1.2F);
                world.createAndScheduleBlockTick(belowPos, VABlocks.CLIMBING_ROPE, 1);
                world.emitGameEvent(null, GameEvent.BLOCK_PLACE, belowPos);
            }
        } else {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!state.canPlaceAt(world, pos)) world.createAndScheduleBlockTick(pos, VABlocks.CLIMBING_ROPE, 1);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isIn(VABlockTags.CLIMBING_ROPES) && (world.getBlockState(pos.down()).isIn(VABlockTags.CLIMBING_ROPES) || state.get(END));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED).add(END);
    }

    static {
        EAST_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 7.0D, 7.0D, 16.0D, 9.0D);
        WEST_SHAPE = Block.createCuboidShape(9.0D, 0.0D, 7.0D, 11.0D, 16.0D, 9.0D);
        SOUTH_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 5.0D, 9.0D, 16.0D, 7.0D);
        NORTH_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 9.0D, 9.0D, 16.0D, 11.0D);
        DOWN_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    }
}

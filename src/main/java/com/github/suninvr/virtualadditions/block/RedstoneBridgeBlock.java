package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class RedstoneBridgeBlock extends Block implements Waterloggable {
    public static final IntProperty POWER = Properties.POWER;
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE_X;
    private static final VoxelShape SHAPE_Y;
    private static final VoxelShape SHAPE_Z;

    public RedstoneBridgeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.DOWN).with(POWER, 0).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER, FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING).getAxis()) {
            case X -> SHAPE_X;
            case Y -> SHAPE_Y;
            case Z -> SHAPE_Z;
        };
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        int power = getPower(world, pos, state);
        if (power == state.get(POWER)) return;
        world.setBlockState(pos, state.with(POWER, power));
        this.updateAffectedNeighbors(world, pos, state);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(FACING).equals(direction.getOpposite()) ? Math.max(0, state.get(POWER) - 1) : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return Math.max(0, getWeakRedstonePower(state, world, pos, direction) - 1);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    protected int getPower(WorldAccess world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING).getOpposite();
        BlockPos blockPos = pos.offset(direction);
        int power = world.getEmittedRedstonePower(blockPos, direction);
        return power;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateAffectedNeighbors(world, pos, state);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState != state) updateAffectedNeighbors(world, pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state != newState) updateAffectedNeighbors(world, pos, state);
    }

    private void updateAffectedNeighbors(World world, BlockPos pos, BlockState state) {
        world.updateNeighborsExcept(pos.offset(state.get(FACING)), this, state.get(FACING).getOpposite());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean bl = ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER);
        BlockState state = getDefaultState().with(FACING, ctx.getSide());
        if (ctx.getPlayer() != null && ctx.getPlayer().isSneaky()) state = state.with(FACING, state.get(FACING).getOpposite());
        return state.with(POWER, getPower(ctx.getWorld(), ctx.getBlockPos(), state)).with(WATERLOGGED, bl);
    }

    static {
        SHAPE_X = Block.createCuboidShape(0, 4, 4, 16, 12, 12);
        SHAPE_Y = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
        SHAPE_Z = Block.createCuboidShape(4, 4, 0, 12, 12, 16);
    }
}

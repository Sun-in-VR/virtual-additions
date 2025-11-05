package com.github.suninvr.virtualadditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RedstoneBridgeBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<RedstoneBridgeBlock> CODEC = simpleCodec(RedstoneBridgeBlock::new);
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE_X;
    private static final VoxelShape SHAPE_Y;
    private static final VoxelShape SHAPE_Z;
    public boolean sendsRedstonePower = true;
    private boolean sendsLessStrongRedstonePower = false;

    public RedstoneBridgeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.DOWN).setValue(POWER, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER, FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING).getAxis()) {
            case X -> SHAPE_X;
            case Y -> SHAPE_Y;
            case Z -> SHAPE_Z;
        };
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        Direction dir = state.getValue(FACING);
        if (dir.getAxis() != Direction.Axis.Y) {
            dir = switch (rotation) {
                case NONE -> dir;
                case CLOCKWISE_90 -> dir.getClockWise();
                case CLOCKWISE_180 -> dir.getOpposite();
                case COUNTERCLOCKWISE_90 -> dir.getCounterClockWise();
            };
        }
        return state.setValue(FACING, dir);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        int power = getPower(world, pos, state);
        if (power == state.getValue(POWER)) return;
        world.setBlockAndUpdate(pos, state.setValue(POWER, power));
        this.updateAffectedNeighbors(world, pos, state);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return this.sendsRedstonePower && state.getValue(FACING).equals(direction.getOpposite()) ? state.getValue(POWER) : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return this.sendsLessStrongRedstonePower ? Math.max(0, getSignal(state, world, pos, direction) - 1) : getSignal(state, world, pos, direction);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    protected int getPower(LevelAccessor world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING).getOpposite();
        BlockPos blockPos = pos.relative(direction);

        this.setWireFlags(false);
        int i = world.getSignal(blockPos, direction);
        this.setWireFlags(true);

        this.sendsLessStrongRedstonePower = true;
        int j = Math.max(0, world.getSignal(blockPos, direction) - 1);
        this.sendsLessStrongRedstonePower = false;

        return Math.max(i, j);
    }

    protected void setWireFlags(boolean bl) {
        this.sendsRedstonePower = bl;
        ((RedStoneWireBlock) Blocks.REDSTONE_WIRE).shouldSignal = bl;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateAffectedNeighbors(world, pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState != state) updateAffectedNeighbors(world, pos, state);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverWorld, BlockPos blockPos, boolean bl) {
        updateAffectedNeighbors(serverWorld, blockPos, blockState);
    }

    private void updateAffectedNeighbors(Level world, BlockPos pos, BlockState state) {
        world.updateNeighborsAtExceptFromFacing(pos.relative(state.getValue(FACING)), this, state.getValue(FACING).getOpposite(), Orientation.fromIndex(0));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean bl = ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER);
        BlockState state = defaultBlockState().setValue(FACING, ctx.getClickedFace());
        return state.setValue(POWER, getPower(ctx.getLevel(), ctx.getClickedPos(), state)).setValue(WATERLOGGED, bl);
    }

    static {
        SHAPE_X = Block.box(0, 4, 4, 16, 12, 12);
        SHAPE_Y = Block.box(4, 0, 4, 12, 16, 12);
        SHAPE_Z = Block.box(4, 4, 0, 12, 12, 16);
    }
}

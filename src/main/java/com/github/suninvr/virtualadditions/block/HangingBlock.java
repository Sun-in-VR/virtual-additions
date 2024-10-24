package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.HangingBlockShape;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HangingBlock extends Block implements Waterloggable {
    public static final MapCodec<HangingBlock> CODEC = createCodec(HangingBlock::new);
    private static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<HangingBlockShape> SHAPE;
    protected static final VoxelShape BOX;

    public HangingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(SHAPE, HangingBlockShape.SINGLE)
        );
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SHAPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOX;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.breakBlock(pos, true);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP){
            if (!this.canPlaceAt(state, world, pos)) {
                tickView.scheduleBlockTick(pos, this, 1);
                return state;
            }
        }
        state = updateState(state, world, pos);
        return state;
    }

    private BlockState updateState(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        if (world.getBlockState(pos.up()).isOf(this)) {
            if (downState.isOf(this)) state = state.with(SHAPE, HangingBlockShape.STRAIGHT);
            else state = state.with(SHAPE, HangingBlockShape.END);
        }  else {
            if (downState.isOf(this)) state = state.with(SHAPE, HangingBlockShape.BASE);
            else state = state.with(SHAPE, HangingBlockShape.SINGLE);}
        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        if (blockState != null) {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            return updateState(blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER), ctx.getWorld(), ctx.getBlockPos());
        } else {
            return null;
        }
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        SHAPE = EnumProperty.of("shape", HangingBlockShape.class);
        BOX = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        return upState.isOf(this) || upState.isSideSolidFullSquare(world, up, Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.GlowingSilkShape;
import com.github.suninvr.virtualadditions.registry.VABlocks;
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
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GlowingSilkBlock extends Block implements Waterloggable {
    private static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<GlowingSilkShape> SHAPE;
    protected static final VoxelShape BOX;

    public GlowingSilkBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(SHAPE, GlowingSilkShape.SINGLE)
        );
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP){
            if (!this.canPlaceAt(state, world, pos)) {
                world.scheduleBlockTick(pos, VABlocks.GLOWING_SILK, 1);
                return state;
            }
        }
        state = updateState(state, world, pos);
        return state;
    }

    private BlockState updateState(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        if (world.getBlockState(pos.up()).isOf(VABlocks.GLOWING_SILK)) {
            if (downState.isOf(VABlocks.GLOWING_SILK)) state = state.with(SHAPE, GlowingSilkShape.STRAIGHT);
            else state = state.with(SHAPE, GlowingSilkShape.END);
        }  else {
            if (downState.isOf(VABlocks.GLOWING_SILK)) state = state.with(SHAPE, GlowingSilkShape.BASE);
            else state = state.with(SHAPE, GlowingSilkShape.SINGLE);}
        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
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
        SHAPE = EnumProperty.of("shape", GlowingSilkShape.class);
        BOX = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        return upState.isOf(VABlocks.GLOWING_SILK) || upState.isSideSolidFullSquare(world, up, Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }
}

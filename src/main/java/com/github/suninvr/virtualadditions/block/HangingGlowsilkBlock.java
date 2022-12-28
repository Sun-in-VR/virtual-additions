package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.HangingGlowsilkShape;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class HangingGlowsilkBlock extends Block implements Waterloggable {
    private static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<HangingGlowsilkShape> SHAPE;
    protected static final VoxelShape BOX;

    public HangingGlowsilkBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(SHAPE, HangingGlowsilkShape.SINGLE)
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
                world.scheduleBlockTick(pos, VABlocks.HANGING_GLOWSILK, 1);
                return state;
            }
        }
        state = updateState(state, world, pos);
        return state;
    }

    private BlockState updateState(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        if (world.getBlockState(pos.up()).isOf(VABlocks.HANGING_GLOWSILK)) {
            if (downState.isOf(VABlocks.HANGING_GLOWSILK)) state = state.with(SHAPE, HangingGlowsilkShape.STRAIGHT);
            else state = state.with(SHAPE, HangingGlowsilkShape.END);
        }  else {
            if (downState.isOf(VABlocks.HANGING_GLOWSILK)) state = state.with(SHAPE, HangingGlowsilkShape.BASE);
            else state = state.with(SHAPE, HangingGlowsilkShape.SINGLE);}
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
        SHAPE = EnumProperty.of("shape", HangingGlowsilkShape.class);
        BOX = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        return upState.isOf(VABlocks.HANGING_GLOWSILK) || upState.isSideSolidFullSquare(world, up, Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.HangingBlockShape;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HangingBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<HangingBlock> CODEC = simpleCodec(HangingBlock::new);
    private static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<HangingBlockShape> SHAPE;
    protected static final VoxelShape BOX;

    public HangingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(SHAPE, HangingBlockShape.SINGLE)
        );
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.destroyBlock(pos, true);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.UP){
            if (!this.canSurvive(state, world, pos)) {
                tickView.scheduleTick(pos, this, 1);
                return state;
            }
        }
        state = updateState(state, world, pos);
        return state;
    }

    private BlockState updateState(BlockState state, LevelReader world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.below());
        if (world.getBlockState(pos.above()).is(this)) {
            if (downState.is(this)) state = state.setValue(SHAPE, HangingBlockShape.STRAIGHT);
            else state = state.setValue(SHAPE, HangingBlockShape.END);
        }  else {
            if (downState.is(this)) state = state.setValue(SHAPE, HangingBlockShape.BASE);
            else state = state.setValue(SHAPE, HangingBlockShape.SINGLE);}
        return state;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = super.getStateForPlacement(ctx);
        if (blockState != null) {
            FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
            return updateState(blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER), ctx.getLevel(), ctx.getClickedPos());
        } else {
            return null;
        }
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = EnumProperty.create("shape", HangingBlockShape.class);
        BOX = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos up = pos.above();
        BlockState upState = world.getBlockState(up);
        return upState.is(this) || upState.isFaceSturdy(world, up, Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}

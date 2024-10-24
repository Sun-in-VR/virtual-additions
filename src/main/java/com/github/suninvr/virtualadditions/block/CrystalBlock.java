package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.CrystalShape;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CrystalBlock extends Block implements Waterloggable {
    public static final MapCodec<CrystalBlock> CODEC = createCodec(CrystalBlock::new);
    public static final EnumProperty<CrystalShape> SHAPE = EnumProperty.of("shape", CrystalShape.class);
    public static final EnumProperty<Direction> POINTING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final VoxelShape BODY_Y_SHAPE;
    public static final VoxelShape TIP_DOWN_SHAPE;
    public static final VoxelShape TIP_UP_SHAPE;
    public static final VoxelShape BODY_X_SHAPE;
    public static final VoxelShape TIP_EAST_SHAPE;
    public static final VoxelShape TIP_WEST_SHAPE;
    public static final VoxelShape BODY_Z_SHAPE;
    public static final VoxelShape TIP_NORTH_SHAPE;
    public static final VoxelShape TIP_SOUTH_SHAPE;

    public CrystalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(SHAPE, CrystalShape.TIP)
                .with(POINTING, Direction.UP)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE).add(POINTING).add(WATERLOGGED);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(POINTING) == Direction.UP && state.get(SHAPE) == CrystalShape.TIP) {
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, world.getDamageSources().stalagmite());

        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }

    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] directions = ctx.getPlacementDirections();
        for (Direction direction : directions) {
            BlockState state = this.getDefaultState().with(POINTING, direction.getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
            if (canPlaceAt(state, ctx.getWorld(), ctx.getBlockPos())) return state;
        }
        return null;

    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(POINTING);
        return this.canPlaceOn(world, new BlockPos(pos.offset(direction.getOpposite())), direction);
    }

    public  boolean canPlaceOn(WorldView world, BlockPos pos, Direction direction) {
        return Block.sideCoversSmallSquare(world, pos, direction) || (world.getBlockState(pos).isIn(VABlockTags.CRYSTALS) && world.getBlockState(pos).get(POINTING) == direction);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.breakBlock(pos, true);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        Direction pointing = state.get(POINTING);
        if (!canPlaceOn(world, new BlockPos(pos.offset(pointing.getOpposite())), pointing)) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        Direction pointing = state.get(POINTING);
        if (direction == pointing) {
            BlockState fromState = world.getBlockState(new BlockPos(pos.offset(direction)));
            if (fromState.isIn(VABlockTags.CRYSTALS) && fromState.get(POINTING) == direction) {
                return state.with(SHAPE, CrystalShape.BODY);
            } else {
                return state.with(SHAPE, CrystalShape.TIP);
            }
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean isBody = state.get(SHAPE) == CrystalShape.BODY;
        Vec3d vec3d = state.getModelOffset(pos);
        return switch (state.get(POINTING)) {
            case UP -> isBody ? BODY_Y_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z) : TIP_UP_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
            case DOWN -> isBody ? BODY_Y_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z) : TIP_DOWN_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
            case NORTH -> isBody ? BODY_Z_SHAPE : TIP_NORTH_SHAPE;
            case SOUTH -> isBody ? BODY_Z_SHAPE : TIP_SOUTH_SHAPE;
            case EAST -> isBody ? BODY_X_SHAPE : TIP_EAST_SHAPE;
            case WEST -> isBody ? BODY_X_SHAPE : TIP_WEST_SHAPE;
        };
    }

    static {
        BODY_Y_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
        TIP_DOWN_SHAPE = Block.createCuboidShape(4.0, 7.0, 4.0, 12.0, 16.0, 12.0);
        TIP_UP_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);

        BODY_X_SHAPE = Block.createCuboidShape(0.0, 4.0, 4.0, 16.0, 12.0, 12.0);
        TIP_EAST_SHAPE = Block.createCuboidShape(0.0, 4.0, 4.0, 9.0, 12.0, 12.0);
        TIP_WEST_SHAPE = Block.createCuboidShape(7.0, 4.0, 4.0, 16.0, 12.0, 12.0);

        BODY_Z_SHAPE = Block.createCuboidShape(4.0, 4.0, 0.0, 12.0, 12.0, 16.0);
        TIP_NORTH_SHAPE = Block.createCuboidShape(4.0, 4.0, 7.0, 12.0, 12.0, 16.0);
        TIP_SOUTH_SHAPE = Block.createCuboidShape(4.0, 4.0, 0.0, 12.0, 12.0, 9.0);
    }
}

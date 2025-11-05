package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.CrystalShape;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CrystalBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<CrystalBlock> CODEC = simpleCodec(CrystalBlock::new);
    public static final EnumProperty<CrystalShape> SHAPE = EnumProperty.create("shape", CrystalShape.class);
    public static final EnumProperty<Direction> POINTING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape BODY_Y_SHAPE;
    public static final VoxelShape TIP_DOWN_SHAPE;
    public static final VoxelShape TIP_UP_SHAPE;
    public static final VoxelShape BODY_X_SHAPE;
    public static final VoxelShape TIP_EAST_SHAPE;
    public static final VoxelShape TIP_WEST_SHAPE;
    public static final VoxelShape BODY_Z_SHAPE;
    public static final VoxelShape TIP_NORTH_SHAPE;
    public static final VoxelShape TIP_SOUTH_SHAPE;

    public CrystalBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(SHAPE, CrystalShape.TIP)
                .setValue(POINTING, Direction.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE).add(POINTING).add(WATERLOGGED);
    }

    public void onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(POINTING) == Direction.UP && state.getValue(SHAPE) == CrystalShape.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, world.damageSources().stalagmite());

        } else {
            super.fallOn(world, state, pos, entity, fallDistance);
        }

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction[] directions = ctx.getNearestLookingDirections();
        for (Direction direction : directions) {
            BlockState state = this.defaultBlockState().setValue(POINTING, direction.getOpposite()).setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
            if (canSurvive(state, ctx.getLevel(), ctx.getClickedPos())) return state;
        }
        return null;

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(POINTING);
        return this.canPlaceOn(world, new BlockPos(pos.relative(direction.getOpposite())), direction);
    }

    public  boolean canPlaceOn(LevelReader world, BlockPos pos, Direction direction) {
        return Block.canSupportCenter(world, pos, direction) || (world.getBlockState(pos).is(VABlockTags.CRYSTALS) && world.getBlockState(pos).getValue(POINTING) == direction);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.destroyBlock(pos, true);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        Direction pointing = state.getValue(POINTING);
        if (!canPlaceOn(world, new BlockPos(pos.relative(pointing.getOpposite())), pointing)) {
            world.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction pointing = state.getValue(POINTING);
        if (direction == pointing) {
            BlockState fromState = world.getBlockState(new BlockPos(pos.relative(direction)));
            if (fromState.is(VABlockTags.CRYSTALS) && fromState.getValue(POINTING) == direction) {
                return state.setValue(SHAPE, CrystalShape.BODY);
            } else {
                return state.setValue(SHAPE, CrystalShape.TIP);
            }
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean isBody = state.getValue(SHAPE) == CrystalShape.BODY;
        Vec3 vec3d = state.getOffset(pos);
        return switch (state.getValue(POINTING)) {
            case UP -> isBody ? BODY_Y_SHAPE.move(vec3d.x, vec3d.y, vec3d.z) : TIP_UP_SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
            case DOWN -> isBody ? BODY_Y_SHAPE.move(vec3d.x, vec3d.y, vec3d.z) : TIP_DOWN_SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
            case NORTH -> isBody ? BODY_Z_SHAPE : TIP_NORTH_SHAPE;
            case SOUTH -> isBody ? BODY_Z_SHAPE : TIP_SOUTH_SHAPE;
            case EAST -> isBody ? BODY_X_SHAPE : TIP_EAST_SHAPE;
            case WEST -> isBody ? BODY_X_SHAPE : TIP_WEST_SHAPE;
        };
    }

    static {
        BODY_Y_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
        TIP_DOWN_SHAPE = Block.box(4.0, 7.0, 4.0, 12.0, 16.0, 12.0);
        TIP_UP_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);

        BODY_X_SHAPE = Block.box(0.0, 4.0, 4.0, 16.0, 12.0, 12.0);
        TIP_EAST_SHAPE = Block.box(0.0, 4.0, 4.0, 9.0, 12.0, 12.0);
        TIP_WEST_SHAPE = Block.box(7.0, 4.0, 4.0, 16.0, 12.0, 12.0);

        BODY_Z_SHAPE = Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 16.0);
        TIP_NORTH_SHAPE = Block.box(4.0, 4.0, 7.0, 12.0, 12.0, 16.0);
        TIP_SOUTH_SHAPE = Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 9.0);
    }
}

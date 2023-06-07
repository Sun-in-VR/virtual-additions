package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SpotlightLightBlock extends Block implements Waterloggable {
    public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static BooleanProperty LIT = Properties.LIT;
    public static EnumProperty<LightStatus> NORTH = EnumProperty.of("north", LightStatus.class);
    public static EnumProperty<LightStatus> EAST = EnumProperty.of("east", LightStatus.class);
    public static EnumProperty<LightStatus> SOUTH = EnumProperty.of("south", LightStatus.class);
    public static EnumProperty<LightStatus> WEST = EnumProperty.of("west", LightStatus.class);
    public static EnumProperty<LightStatus> UP = EnumProperty.of("up", LightStatus.class);
    public static EnumProperty<LightStatus> DOWN = EnumProperty.of("down", LightStatus.class);
    private static final VoxelShape box = VoxelShapes.cuboid(0, 0, 0, 1, 1, 1);

    public SpotlightLightBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(LIT, false)
                .with(NORTH, LightStatus.NONE)
                .with(EAST, LightStatus.NONE)
                .with(SOUTH, LightStatus.NONE)
                .with(WEST, LightStatus.NONE)
                .with(UP, LightStatus.NONE)
                .with(DOWN, LightStatus.NONE)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isHolding(Items.LIGHT)) return box;
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Nullable
    public static LightStatus getStatus(BlockState state, Direction direction) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            return switch (direction) {
                case NORTH -> state.get(NORTH);
                case EAST -> state.get(EAST);
                case SOUTH -> state.get(SOUTH);
                case WEST -> state.get(WEST);
                case UP -> state.get(UP);
                case DOWN -> state.get(DOWN);
            };
        }
        return null;
    }
    
    public static void setStatus(World world, BlockState state, BlockPos pos, Direction direction, LightStatus status) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            state = getStateWithStatus(state, direction, status);
            world.setBlockState(pos, state);
            updateLight(world, state, pos);
        }
    }

    public static boolean isLit(BlockState state) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            LightStatus status;
            for (Direction direction : Direction.values()) {
                status = getStatus(state, direction);
                if (status == LightStatus.LIT) return true;
            }
        }
        return false;
    }

    public static boolean shouldRemove(BlockState state) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            LightStatus status;
            for (Direction direction : Direction.values()) { 
                status = getStatus(state, direction);
                if (status != LightStatus.NONE) return false;
            }
        }
        return true;
    }

    public static void updateLight(World world, BlockState state, BlockPos pos) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            if (shouldRemove(state)) {
                world.setBlockState(pos, state.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
                return;
            }
            world.setBlockState(pos, state.with(LIT, isLit(state)));
        }
    }

    public static BlockState getStateWithStatus(BlockState state, Direction direction, LightStatus status) {
        if (state.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            state = switch (direction) {
                case NORTH -> state.with(NORTH, status);
                case EAST -> state.with(EAST, status);
                case SOUTH -> state.with(SOUTH, status);
                case WEST -> state.with(WEST, status);
                case UP -> state.with(UP, status);
                case DOWN -> state.with(DOWN, status);
            };
        }
        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(LIT).add(NORTH).add(EAST).add(SOUTH).add(WEST).add(UP).add(DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return state.with(LIT, isLit(state));
    }
}

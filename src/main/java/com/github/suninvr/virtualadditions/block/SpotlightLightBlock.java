package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.SpotlightLightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SpotlightLightBlock extends BlockWithEntity implements Waterloggable {
    public static final MapCodec<SpotlightLightBlock> CODEC = createCodec(SpotlightLightBlock::new);
    public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static BooleanProperty LIT = Properties.LIT;
    public static EnumProperty<LightStatus> NORTH = EnumProperty.of("north", LightStatus.class);
    public static EnumProperty<LightStatus> EAST = EnumProperty.of("east", LightStatus.class);
    public static EnumProperty<LightStatus> SOUTH = EnumProperty.of("south", LightStatus.class);
    public static EnumProperty<LightStatus> WEST = EnumProperty.of("west", LightStatus.class);
    public static EnumProperty<LightStatus> UP = EnumProperty.of("up", LightStatus.class);
    public static EnumProperty<LightStatus> DOWN = EnumProperty.of("down", LightStatus.class);

    public SpotlightLightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
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

    public static void updateSources(World world, BlockPos pos, BlockState state) {
        if (!state.isOf(VABlocks.SPOTLIGHT_LIGHT)) return;
        SpotlightLightBlock.getSources(world, pos, state).forEach(pos2 -> world.scheduleBlockTick(pos2, VABlocks.SPOTLIGHT, 1));
    }

    public static List<BlockPos> getSources(World world, BlockPos pos, BlockState state) {
        List<BlockPos> posList = new ArrayList<>();
        for (Direction dir : Direction.values()) if (state.get(getDirectionProperty(dir)).hasLight()) posList.add(findSource(world, pos, dir));
        return posList;
    }

    protected static BlockPos findSource(World world, BlockPos pos, Direction dir) {
        int i = 0;
        BlockPos blockPos = BlockPos.ORIGIN;
        while (i < 33) {
            i++;
            blockPos = pos.offset(dir, i);
            if (world.getBlockEntity(blockPos) instanceof SpotlightBlockEntity spotlightBlockEntity && spotlightBlockEntity.getLightLocation().equals(pos)) return blockPos;
        }
        return blockPos;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient() && !state.isOf(newState.getBlock()) && !newState.isAir()) {
            updateSources(world, pos, state);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    protected static EnumProperty<LightStatus> getDirectionProperty(Direction dir) {
        return switch (dir) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
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

    public static BlockState getUpdatedLightState(BlockState state) {
        if (!state.isOf(VABlocks.SPOTLIGHT_LIGHT)) return state;
        if (shouldRemove(state)) return state.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
        return state.with(LIT, isLit(state));
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpotlightLightBlockEntity(pos, state);
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.SpotlightLightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SpotlightLightBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<SpotlightLightBlock> CODEC = simpleCodec(SpotlightLightBlock::new);
    public static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static BooleanProperty LIT = BlockStateProperties.LIT;
    public static EnumProperty<LightStatus> NORTH = EnumProperty.create("north", LightStatus.class);
    public static EnumProperty<LightStatus> EAST = EnumProperty.create("east", LightStatus.class);
    public static EnumProperty<LightStatus> SOUTH = EnumProperty.create("south", LightStatus.class);
    public static EnumProperty<LightStatus> WEST = EnumProperty.create("west", LightStatus.class);
    public static EnumProperty<LightStatus> UP = EnumProperty.create("up", LightStatus.class);
    public static EnumProperty<LightStatus> DOWN = EnumProperty.create("down", LightStatus.class);

    public SpotlightLightBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(LIT, false)
                .setValue(NORTH, LightStatus.NONE)
                .setValue(EAST, LightStatus.NONE)
                .setValue(SOUTH, LightStatus.NONE)
                .setValue(WEST, LightStatus.NONE)
                .setValue(UP, LightStatus.NONE)
                .setValue(DOWN, LightStatus.NONE)
        );
    }

    public static void updateSources(LevelAccessor world, BlockPos pos, BlockState state) {
        if (!state.is(VABlocks.SPOTLIGHT_LIGHT)) return;
        SpotlightLightBlock.getSources(world, pos, state).forEach(pos2 -> world.scheduleTick(pos2, VABlocks.SPOTLIGHT, 1));
    }

    public static List<BlockPos> getSources(LevelAccessor world, BlockPos pos, BlockState state) {
        List<BlockPos> posList = new ArrayList<>();
        for (Direction dir : Direction.values()) if (state.getValue(getDirectionProperty(dir)).hasLight()) posList.add(findSource(world, pos, dir));
        return posList;
    }

    protected static BlockPos findSource(LevelAccessor world, BlockPos pos, Direction dir) {
        int i = 0;
        BlockPos blockPos = BlockPos.ZERO;
        while (i < 33) {
            i++;
            blockPos = pos.relative(dir, i);
            if (world.getBlockEntity(blockPos) instanceof SpotlightBlockEntity spotlightBlockEntity && spotlightBlockEntity.getLightLocation().equals(pos)) return blockPos;
        }
        return blockPos;
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
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    @Nullable
    public static LightStatus getStatus(BlockState state, Direction direction) {
        if (state.is(VABlocks.SPOTLIGHT_LIGHT)) {
            return switch (direction) {
                case NORTH -> state.getValue(NORTH);
                case EAST -> state.getValue(EAST);
                case SOUTH -> state.getValue(SOUTH);
                case WEST -> state.getValue(WEST);
                case UP -> state.getValue(UP);
                case DOWN -> state.getValue(DOWN);
            };
        }
        return null;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        LightStatus north = getStatus(state, rotation.rotate(Direction.SOUTH));
        LightStatus east = getStatus(state, rotation.rotate(Direction.WEST));
        LightStatus south = getStatus(state, rotation.rotate(Direction.NORTH));
        LightStatus west = getStatus(state, rotation.rotate(Direction.EAST));
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        LightStatus north = getStatus(state, mirror.getRotation(Direction.NORTH).rotate(Direction.NORTH));
        LightStatus east = getStatus(state, mirror.getRotation(Direction.EAST).rotate(Direction.EAST));
        LightStatus south = getStatus(state, mirror.getRotation(Direction.SOUTH).rotate(Direction.SOUTH));
        LightStatus west = getStatus(state, mirror.getRotation(Direction.WEST).rotate(Direction.WEST));
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    public static boolean isLit(BlockState state) {
        if (state.is(VABlocks.SPOTLIGHT_LIGHT)) {
            LightStatus status;
            for (Direction direction : Direction.values()) {
                status = getStatus(state, direction);
                if (status == LightStatus.LIT) return true;
            }
        }
        return false;
    }

    public static boolean shouldRemove(BlockState state) {
        if (state.is(VABlocks.SPOTLIGHT_LIGHT)) {
            LightStatus status;
            for (Direction direction : Direction.values()) { 
                status = getStatus(state, direction);
                if (status != LightStatus.NONE) return false;
            }
        }
        return true;
    }

    public static BlockState getUpdatedLightState(BlockState state) {
        if (!state.is(VABlocks.SPOTLIGHT_LIGHT)) return state;
        if (shouldRemove(state)) return state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        return state.setValue(LIT, isLit(state));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(LIT).add(NORTH).add(EAST).add(SOUTH).add(WEST).add(UP).add(DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return state.setValue(LIT, isLit(state));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpotlightLightBlockEntity(pos, state);
    }
}

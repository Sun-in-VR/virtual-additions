package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class SpotlightBlock extends BaseEntityBlock {
    public static final MapCodec<SpotlightBlock> CODEC = simpleCodec(SpotlightBlock::new);
    public static final EnumProperty<FrontAndTop> ORIENTATION = BlockStateProperties.ORIENTATION;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public SpotlightBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(ORIENTATION, FrontAndTop.EAST_UP)
                .setValue(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION).add(POWERED);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (state.is(this) && state.getValue(POWERED)) updateLightLocation(world, pos, state);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        updateLightLocation(world, pos, state);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        boolean redstonePower = world.hasNeighborSignal(pos);
        boolean isPowered = state.getValue(POWERED);
        if (redstonePower && !isPowered) {
            updateLightLocation(world, pos, state);
            world.setBlockAndUpdate(pos, state.setValue(POWERED, true));
            setLightState(world, pos, LightStatus.LIT);
        } else if (!redstonePower && isPowered) {
            world.setBlockAndUpdate(pos, state.setValue(POWERED, false));
            setLightState(world, pos, LightStatus.UNLIT);
        }
    }

    public static void updateLightLocation(Level world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) return;
        if (!state.is(VABlocks.SPOTLIGHT)) return;
        SpotlightBlockEntity blockEntity = world.getBlockEntity(pos) instanceof SpotlightBlockEntity spotlightBlockEntity ? spotlightBlockEntity : null;
        if (blockEntity == null) return;

        //Initialize position and state variables.
        Direction direction = state.getValue(SpotlightBlock.ORIENTATION).front();
        BlockPos newLightPos = findLightLocation(world, pos, direction);
        BlockPos oldLightPos = blockEntity.getLightLocation();
        BlockState newLightState = world.getBlockState(newLightPos);
        BlockState oldLightState = world.getBlockState(oldLightPos);
        boolean isWater = newLightState.is(Blocks.WATER) && world.getFluidState(newLightPos).isSourceOfType(Fluids.WATER);

        if (newLightState.is(VABlocks.SPOTLIGHT_LIGHT) || newLightState.isAir() || isWater) {
            BlockState lightState = SpotlightBlock.getLightState(world, pos, (newLightState.is(VABlocks.SPOTLIGHT_LIGHT) ? newLightState : VABlocks.SPOTLIGHT_LIGHT.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, isWater)));
            world.setBlockAndUpdate(newLightPos, lightState);
            blockEntity.setLightLocation(newLightPos);
        }

        if (!oldLightPos.equals(newLightPos) && oldLightState.is(VABlocks.SPOTLIGHT_LIGHT)) {
            BlockState lightState = SpotlightBlock.getLightState(state, oldLightState, LightStatus.NONE);
            world.setBlockAndUpdate(oldLightPos, lightState);
        }
    }

    private static BlockPos findLightLocation(Level world, BlockPos startPos, Direction direction) {
        BlockPos pos = new BlockPos(startPos.relative(direction));
        if (!world.isEmptyBlock(pos) && !world.getBlockState(pos).is(VABlockTags.SPOTLIGHT_PERMEABLE)) return pos;
        int i = 0;

        BlockPos finalPos = pos;
        BlockPos offsetPos = new BlockPos(pos.relative(direction));
        BlockState offsetState = world.getBlockState(offsetPos);
        while ((world.isEmptyBlock(offsetPos) || offsetState.is(VABlocks.SPOTLIGHT_LIGHT) || offsetState.is(VABlockTags.SPOTLIGHT_PERMEABLE)) && i < 31) {
            pos = new BlockPos(pos.relative(direction));
            offsetPos = new BlockPos(pos.relative(direction));
            offsetState = world.getBlockState(offsetPos);
            i++;
            if (world.isEmptyBlock(pos) || world.getBlockState(pos).is(Blocks.WATER) || world.getBlockState(pos).is(VABlocks.SPOTLIGHT_LIGHT)) finalPos = pos;
        }

        return finalPos;
    }

    public static void setLightState(Level world, BlockPos pos, LightStatus status) {
        setLightState(world, pos, world.getBlockState(pos), status);
    }

    public static void setLightState(Level world, BlockPos pos, BlockState state, LightStatus status) {
        if (!state.is(VABlocks.SPOTLIGHT)) return;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClientSide() && blockEntity instanceof SpotlightBlockEntity spotlightBlockEntity) {
            BlockPos lightPos = spotlightBlockEntity.getLightLocation();
            setLightState(world, state, lightPos, world.getBlockState(lightPos), status);
        }
    }

    public static void setLightState(Level world, BlockState state, BlockPos lightPos, BlockState lightState, LightStatus status) {
        if (!state.is(VABlocks.SPOTLIGHT)) return;
        BlockState updatedLightState = getLightState(state, lightState, status);
        world.setBlockAndUpdate(lightPos, updatedLightState);
    }

    public static BlockState getLightState(Level world, BlockPos pos, BlockState lightState) {
        BlockState state = world.getBlockState(pos);
        if (!state.is(VABlocks.SPOTLIGHT)) return lightState;
        LightStatus status = state.getValue(POWERED) ? LightStatus.LIT : LightStatus.UNLIT;
        return getLightState(state, lightState, status);
    }

    public static BlockState getLightState(BlockState state, BlockState lightState, LightStatus status) {
        if (!lightState.is(VABlocks.SPOTLIGHT_LIGHT)) return lightState;
        if (!state.is(VABlocks.SPOTLIGHT)) return lightState;
        Direction direction = state.getValue(ORIENTATION).front().getOpposite();
        return SpotlightLightBlock.getUpdatedLightState(lightState.setValue(SpotlightLightBlock.getDirectionProperty(direction), status));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpotlightBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction facingDirection = ctx.getNearestLookingDirection();
        Direction rotationDirection = switch (facingDirection) {
            case DOWN -> ctx.getHorizontalDirection();
            case UP -> ctx.getHorizontalDirection().getOpposite();
            case NORTH, SOUTH, WEST, EAST -> Direction.UP;
        };
        return this.defaultBlockState().setValue(ORIENTATION, FrontAndTop.fromFrontAndTop(facingDirection, rotationDirection)).setValue(POWERED, world.hasNeighborSignal(pos));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ORIENTATION, rotation.rotation().rotate((FrontAndTop)state.getValue(ORIENTATION)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ORIENTATION, mirror.rotation().rotate((FrontAndTop)state.getValue(ORIENTATION)));
    }
}

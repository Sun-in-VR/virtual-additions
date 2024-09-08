package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Orientation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class SpotlightBlock extends BlockWithEntity {
    public static final MapCodec<SpotlightBlock> CODEC = createCodec(SpotlightBlock::new);
    public static final EnumProperty<Orientation> ORIENTATION = Properties.ORIENTATION;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public SpotlightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(ORIENTATION, Orientation.EAST_UP)
                .with(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION).add(POWERED);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (state.isOf(this) && state.get(POWERED)) updateLightLocation(world, pos, state);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            setLightState(world, pos, state, LightStatus.NONE);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateLightLocation(world, pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        boolean redstonePower = world.isReceivingRedstonePower(pos);
        boolean isPowered = state.get(POWERED);
        if (redstonePower && !isPowered) {
            updateLightLocation(world, pos, state);
            world.setBlockState(pos, state.with(POWERED, true));
            setLightState(world, pos, LightStatus.LIT);
        } else if (!redstonePower && isPowered) {
            world.setBlockState(pos, state.with(POWERED, false));
            setLightState(world, pos, LightStatus.UNLIT);
        }
    }

    public static void updateLightLocation(World world, BlockPos pos, BlockState state) {
        if (world.isClient) return;
        if (!state.isOf(VABlocks.SPOTLIGHT)) return;
        SpotlightBlockEntity blockEntity = world.getBlockEntity(pos) instanceof SpotlightBlockEntity spotlightBlockEntity ? spotlightBlockEntity : null;
        if (blockEntity == null) return;

        //Initialize position and state variables.
        Direction direction = state.get(SpotlightBlock.ORIENTATION).getFacing();
        BlockPos newLightPos = findLightLocation(world, pos, direction);
        BlockPos oldLightPos = blockEntity.getLightLocation();
        BlockState newLightState = world.getBlockState(newLightPos);
        BlockState oldLightState = world.getBlockState(oldLightPos);
        boolean isWater = newLightState.isOf(Blocks.WATER) && world.getFluidState(newLightPos).isEqualAndStill(Fluids.WATER);

        if (newLightState.isOf(VABlocks.SPOTLIGHT_LIGHT) || newLightState.isAir() || isWater) {
            BlockState lightState = SpotlightBlock.getLightState(world, pos, (newLightState.isOf(VABlocks.SPOTLIGHT_LIGHT) ? newLightState : VABlocks.SPOTLIGHT_LIGHT.getDefaultState().with(Properties.WATERLOGGED, isWater)));
            world.setBlockState(newLightPos, lightState);
            blockEntity.setLightLocation(newLightPos);
        }

        if (!oldLightPos.equals(newLightPos) && oldLightState.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            BlockState lightState = SpotlightBlock.getLightState(state, oldLightState, LightStatus.NONE);
            world.setBlockState(oldLightPos, lightState);
        }
    }

    private static BlockPos findLightLocation(World world, BlockPos startPos, Direction direction) {
        BlockPos pos = new BlockPos(startPos.offset(direction));
        if (!world.isAir(pos) && !world.getBlockState(pos).isIn(VABlockTags.SPOTLIGHT_PERMEABLE)) return pos;
        int i = 0;

        BlockPos finalPos = pos;
        BlockPos offsetPos = new BlockPos(pos.offset(direction));
        BlockState offsetState = world.getBlockState(offsetPos);
        while ((world.isAir(offsetPos) || offsetState.isOf(VABlocks.SPOTLIGHT_LIGHT) || offsetState.isIn(VABlockTags.SPOTLIGHT_PERMEABLE)) && i < 31) {
            pos = new BlockPos(pos.offset(direction));
            offsetPos = new BlockPos(pos.offset(direction));
            offsetState = world.getBlockState(offsetPos);
            i++;
            if (world.isAir(pos) || world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos).isOf(VABlocks.SPOTLIGHT_LIGHT)) finalPos = pos;
        }

        return finalPos;
    }

    public static void setLightState(World world, BlockPos pos, LightStatus status) {
        setLightState(world, pos, world.getBlockState(pos), status);
    }

    public static void setLightState(World world, BlockPos pos, BlockState state, LightStatus status) {
        if (!state.isOf(VABlocks.SPOTLIGHT)) return;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient() && blockEntity instanceof SpotlightBlockEntity spotlightBlockEntity) {
            BlockPos lightPos = spotlightBlockEntity.getLightLocation();
            setLightState(world, state, lightPos, world.getBlockState(lightPos), status);
        }
    }

    public static void setLightState(World world, BlockState state, BlockPos lightPos, BlockState lightState, LightStatus status) {
        if (!state.isOf(VABlocks.SPOTLIGHT)) return;
        BlockState updatedLightState = getLightState(state, lightState, status);
        world.setBlockState(lightPos, updatedLightState);
    }

    public static BlockState getLightState(World world, BlockPos pos, BlockState lightState) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(VABlocks.SPOTLIGHT)) return lightState;
        LightStatus status = state.get(POWERED) ? LightStatus.LIT : LightStatus.UNLIT;
        return getLightState(state, lightState, status);
    }

    public static BlockState getLightState(BlockState state, BlockState lightState, LightStatus status) {
        if (!lightState.isOf(VABlocks.SPOTLIGHT_LIGHT)) return lightState;
        if (!state.isOf(VABlocks.SPOTLIGHT)) return lightState;
        Direction direction = state.get(ORIENTATION).getFacing().getOpposite();
        return SpotlightLightBlock.getUpdatedLightState(lightState.with(SpotlightLightBlock.getDirectionProperty(direction), status));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpotlightBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction facingDirection = ctx.getPlayerLookDirection();
        Direction rotationDirection = switch (facingDirection) {
            case DOWN -> ctx.getHorizontalPlayerFacing();
            case UP -> ctx.getHorizontalPlayerFacing().getOpposite();
            case NORTH, SOUTH, WEST, EAST -> Direction.UP;
        };
        return this.getDefaultState().with(ORIENTATION, Orientation.byDirections(facingDirection, rotationDirection)).with(POWERED, world.isReceivingRedstonePower(pos));
    }
}

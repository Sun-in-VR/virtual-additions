package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Orientation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        setLightState(world, pos, LightStatus.NONE);
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateLightLocation(world, pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
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
        BlockPos newPos = findLightLocation(world, pos, direction);
        BlockPos oldPos = blockEntity.getLightLocation();
        BlockState newState = world.getBlockState(newPos);
        BlockState oldState = world.getBlockState(oldPos);
        boolean isWater = newState.isOf(Blocks.WATER) && world.getFluidState(newPos).isEqualAndStill(Fluids.WATER);

        if (newState.isOf(VABlocks.SPOTLIGHT_LIGHT) || newState.isAir() || isWater) {
            BlockState lightState = SpotlightBlock.getLightState(world, pos, (newState.isOf(VABlocks.SPOTLIGHT_LIGHT) ? newState : VABlocks.SPOTLIGHT_LIGHT.getDefaultState().with(Properties.WATERLOGGED, isWater)));
            world.setBlockState(newPos, lightState);
            blockEntity.setLightLocation(newPos);
        }

        if (!oldPos.equals(newPos) && oldState.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
            BlockState lightState = SpotlightBlock.getLightState(world, pos, oldState, LightStatus.NONE);
            world.setBlockState(oldPos, lightState);
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
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient() && blockEntity instanceof SpotlightBlockEntity spotlightBlockEntity) {
            BlockPos lightPos = spotlightBlockEntity.getLightLocation();
            setLightState(world, pos, lightPos, status);
        }
    }

    public static void setLightState(World world, BlockPos pos, BlockPos lightPos, LightStatus status) {
        setLightState(world, pos, lightPos, world.getBlockState(lightPos), status);
    }

    public static void setLightState(World world, BlockPos pos, BlockPos lightPos, BlockState lightState, LightStatus status) {
        BlockState updatedLightState = getLightState(world, pos, lightState, status);
        world.setBlockState(lightPos, updatedLightState);
    }

    public static BlockState getLightState(World world, BlockPos pos, BlockState lightState) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(VABlocks.SPOTLIGHT)) return lightState;
        LightStatus status = state.get(POWERED) ? LightStatus.LIT : LightStatus.UNLIT;
        return getLightState(world, pos, lightState, status);
    }

    public static BlockState getLightState(World world, BlockPos pos, BlockState lightState, LightStatus status) {
        if (!lightState.isOf(VABlocks.SPOTLIGHT_LIGHT)) return lightState;
        BlockState state = world.getBlockState(pos);
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

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ClimbingRopeAnchorBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HOPPER_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty END = BooleanProperty.of("end");
    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape EAST_BOTTOM_SHAPE;
    protected static final VoxelShape WEST_BOTTOM_SHAPE;
    protected static final VoxelShape SOUTH_BOTTOM_SHAPE;
    protected static final VoxelShape NORTH_BOTTOM_SHAPE;
    protected static final VoxelShape DOWN_SHAPE;

    protected static final VoxelShape ATTACHABLE_HIGH;
    protected static final VoxelShape ATTACHABLE_LOW;

    public ClimbingRopeAnchorBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.DOWN)
                .with(HALF, BlockHalf.TOP)
                .with(WATERLOGGED, false)
                .with(END, true)
        );
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF)==BlockHalf.BOTTOM) {
            return switch (state.get(FACING)) {
                case NORTH -> NORTH_BOTTOM_SHAPE;
                case EAST -> EAST_BOTTOM_SHAPE;
                case SOUTH -> SOUTH_BOTTOM_SHAPE;
                case WEST -> WEST_BOTTOM_SHAPE;
                default -> DOWN_SHAPE;
            };
        }
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> DOWN_SHAPE;
        };
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.scheduleBlockTick( pos, state.getBlock(), 0 );
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        if (world instanceof ServerWorld serverWorld) {
            getDroppedStacks(state, serverWorld, pos, blockEntity, null, stack).forEach((stackx) -> {
                if (!player.getInventory().insertStack(stackx)) {
                    dropStack(world, pos, stackx);
                }
            });
            state.onStacksDropped(serverWorld, pos, stack, true);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.canPlaceAt(world,pos)) {
            BlockPos belowPos = new BlockPos(pos.down());
            BlockState belowState = world.getBlockState(belowPos);
            if (world.getBottomY() <= belowPos.getY() && (belowState.canReplace(new AutomaticItemPlacementContext(world, belowPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) && !belowState.isOf(Blocks.LAVA)) {
                BlockState newState = VABlocks.CLIMBING_ROPE.getDefaultState().with(ClimbingRopeBlock.FACING, state.get(FACING)).with(ClimbingRopeBlock.WATERLOGGED, world.getFluidState(belowPos).getFluid() == Fluids.WATER);
                world.setBlockState(belowPos, newState);
                world.setBlockState(pos, state.with(END, false));
                world.playSound(null, belowPos, VASoundEvents.BLOCK_ROPE_EXTEND, SoundCategory.BLOCKS, 1F, 0.8F);
                world.scheduleBlockTick(belowPos, newState.getBlock(), 1);
                world.emitGameEvent(null, GameEvent.BLOCK_PLACE, belowPos);
            }
        } else {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!state.canPlaceAt(world, pos)) world.scheduleBlockTick(pos, VABlocks.CLIMBING_ROPE_ANCHOR, 1);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockHalf height = state.get(HALF);
        boolean bl = this.canPlaceOn(world, new BlockPos(pos.offset(direction.getOpposite())), direction, height);
        return state.get(END) ? bl : bl && world.getBlockState(new BlockPos(pos.down())).isIn(VABlockTags.CLIMBING_ROPES);
    }

    private boolean canPlaceOn(WorldView world, BlockPos pos, Direction direction, BlockHalf height) {
        if (direction == Direction.DOWN) return Block.sideCoversSmallSquare(world, pos, direction);

        BlockState placeOnState = world.getBlockState(pos);
        if (height == BlockHalf.BOTTOM) return !VoxelShapes.matchesAnywhere(placeOnState.getSidesShape(world, pos).getFace(direction), ATTACHABLE_LOW, BooleanBiFunction.ONLY_SECOND);
        return !VoxelShapes.matchesAnywhere(placeOnState.getSidesShape(world, pos).getFace(direction), ATTACHABLE_HIGH, BooleanBiFunction.ONLY_SECOND);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState blockState = this.getDefaultState();
        Direction[] directions = ctx.getPlacementDirections();
        for (Direction direction : directions) {
            World world = ctx.getWorld();
            BlockPos pos = ctx.getBlockPos();
            BlockHalf half = getPlacementHeight(world, pos.offset(direction), direction.getOpposite());
            if (half == null) continue;

            if (direction == Direction.DOWN) continue;
            blockState = blockState.with(FACING, direction.getOpposite()).with(HALF, half);
            if(blockState.canPlaceAt(world, pos)) {
                return blockState.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
            }
        }
        return null;
    }

    public static BlockHalf getPlacementHeight(WorldView world, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) return BlockHalf.TOP;

        BlockState placeOnState = world.getBlockState(pos);
        if (!VoxelShapes.matchesAnywhere(placeOnState.getSidesShape(world, pos).getFace(direction), ATTACHABLE_HIGH, BooleanBiFunction.ONLY_SECOND)) return BlockHalf.TOP;
        if (!VoxelShapes.matchesAnywhere(placeOnState.getSidesShape(world, pos).getFace(direction), ATTACHABLE_LOW, BooleanBiFunction.ONLY_SECOND)) return BlockHalf.BOTTOM;
        return BlockHalf.TOP;
    }



    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HALF).add(WATERLOGGED).add(END);
    }

    static {
        EAST_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0D, 0.0D, 7.0D, 7.0D, 12.0D, 9.0D),
                Block.createCuboidShape(4.0D, 12.0D, 7.0D, 8.0D, 16.0D, 9.0D)
        );
        WEST_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(9.0D, 0.0D, 7.0D, 11.0D, 12.0D, 9.0D),
                Block.createCuboidShape(8.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D)
        );
        SOUTH_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7.0D, 0.0D, 5.0D, 9.0D, 12.0D, 7.0D),
                Block.createCuboidShape(7.0D, 12.0D, 4.0D, 9.0D, 16.0D, 8.0D)
        );
        NORTH_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7.0D, 0.0D, 9.0D, 9.0D, 12.0D, 11.0D),
                Block.createCuboidShape(7.0D, 12.0D, 8.0D, 9.0D, 16.0D, 12.0D)
        );
        EAST_BOTTOM_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0D, 0.0D, 7.0D, 7.0D, 4.0D, 9.0D),
                Block.createCuboidShape(4.0D, 4.0D, 7.0D, 8.0D, 8.0D, 9.0D)
        );
        WEST_BOTTOM_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(9.0D, 0.0D, 7.0D, 11.0D, 4.0D, 9.0D),
                Block.createCuboidShape(8.0D, 4.0D, 7.0D, 12.0D, 8.0D, 9.0D)
        );
        SOUTH_BOTTOM_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7.0D, 0.0D, 5.0D, 9.0D, 4.0D, 7.0D),
                Block.createCuboidShape(7.0D, 4.0D, 4.0D, 9.0D, 8.0D, 8.0D)
        );
        NORTH_BOTTOM_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7.0D, 0.0D, 9.0D, 9.0D, 4.0D, 11.0D),
                Block.createCuboidShape(7.0D, 4.0D, 8.0D, 9.0D, 8.0D, 12.0D)
        );
        DOWN_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 8.0D, 9.0D),
                Block.createCuboidShape(7.0D, 8.0D, 6.0D, 9.0D, 12.0D, 10.0D)
        );
        ATTACHABLE_HIGH = VoxelShapes.combineAndSimplify(
                Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.createCuboidShape(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D),
                BooleanBiFunction.ONLY_FIRST
        );
        ATTACHABLE_LOW = VoxelShapes.combineAndSimplify(
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
                BooleanBiFunction.ONLY_FIRST
        );
    }
}

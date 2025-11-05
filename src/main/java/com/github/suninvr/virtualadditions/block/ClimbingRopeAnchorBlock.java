package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ClimbingRopeAnchorBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<ClimbingRopeAnchorBlock> CODEC = simpleCodec(ClimbingRopeAnchorBlock::new);
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty END = BooleanProperty.create("end");
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape EAST_BOTTOM_SHAPE;
    protected static final VoxelShape WEST_BOTTOM_SHAPE;
    protected static final VoxelShape SOUTH_BOTTOM_SHAPE;
    protected static final VoxelShape NORTH_BOTTOM_SHAPE;
    protected static final VoxelShape UP_SHAPE;

    protected static final VoxelShape ATTACHABLE_HIGH;
    protected static final VoxelShape ATTACHABLE_LOW;

    public ClimbingRopeAnchorBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.UP)
                .setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, false)
                .setValue(END, true)
        );
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF)==Half.BOTTOM) {
            return switch (state.getValue(FACING)) {
                case NORTH -> NORTH_BOTTOM_SHAPE;
                case EAST -> EAST_BOTTOM_SHAPE;
                case SOUTH -> SOUTH_BOTTOM_SHAPE;
                case WEST -> WEST_BOTTOM_SHAPE;
                default -> UP_SHAPE;
            };
        }
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> UP_SHAPE;
        };
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        world.scheduleTick( pos, state.getBlock(), 0 );
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (state.canSurvive(world,pos)) {
            if (!state.getValue(END)) return;
            BlockPos belowPos = new BlockPos(pos.below());
            BlockState belowState = world.getBlockState(belowPos);
            if (world.getMinY() <= belowPos.getY() && (belowState.canBeReplaced(new DirectionalPlaceContext(world, belowPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) && !belowState.is(Blocks.LAVA)) {
                BlockState newState = VABlocks.CLIMBING_ROPE.defaultBlockState().setValue(ClimbingRopeBlock.FACING, state.getValue(FACING)).setValue(ClimbingRopeBlock.WATERLOGGED, world.getFluidState(belowPos).getType() == Fluids.WATER);
                world.setBlockAndUpdate(belowPos, newState);
                world.setBlockAndUpdate(pos, state.setValue(END, false));
                world.playSound(null, belowPos, VASoundEvents.BLOCK_ROPE_EXTEND, SoundSource.BLOCKS, 1F, 0.8F);
                world.scheduleTick(belowPos, newState.getBlock(), 1);
                world.gameEvent(null, GameEvent.BLOCK_PLACE, belowPos);
            }
        } else {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!state.getValue(END) && stack.is(Items.SHEARS)) {
            world.setBlockAndUpdate(pos, state.setValue(END, true));
            if (world.getBlockState(pos.below()).is(VABlocks.CLIMBING_ROPE)) world.destroyBlock(pos.below(), true, player);
            stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
            world.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        if (!state.canSurvive(world, pos)) world.scheduleTick(pos, this, 1);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        Half height = state.getValue(HALF);
        boolean bl = this.canPlaceOn(world, new BlockPos(pos.relative(direction)), direction, height);
        return state.getValue(END) ? bl : bl && world.getBlockState(new BlockPos(pos.below())).is(VABlockTags.CLIMBING_ROPES);
    }

    public static boolean canPlaceAt(Block block, BlockState state, LevelReader world, BlockPos pos) {
        return block instanceof ClimbingRopeAnchorBlock climbingRopeAnchorBlock && climbingRopeAnchorBlock.canSurvive(state, world, pos);
    }

    private boolean canPlaceOn(LevelReader world, BlockPos pos, Direction direction, Half height) {
        if (direction == Direction.UP) return Block.canSupportCenter(world, pos, Direction.DOWN);

        BlockState placeOnState = world.getBlockState(pos);
        if (height == Half.BOTTOM) return !Shapes.joinIsNotEmpty(placeOnState.getBlockSupportShape(world, pos).getFaceShape(direction), ATTACHABLE_LOW, BooleanOp.ONLY_SECOND);
        return !Shapes.joinIsNotEmpty(placeOnState.getBlockSupportShape(world, pos).getFaceShape(direction), ATTACHABLE_HIGH, BooleanOp.ONLY_SECOND);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        BlockState blockState = this.defaultBlockState();
        Direction[] directions = ctx.getNearestLookingDirections();
        for (Direction direction : directions) {
            Level world = ctx.getLevel();
            BlockPos pos = ctx.getClickedPos();
            Half half = getPlacementHeight(world, pos.relative(direction), direction.getOpposite());
            if (half == null) continue;

            if (direction == Direction.DOWN) continue;
            blockState = blockState.setValue(FACING, direction).setValue(HALF, half);
            if(blockState.canSurvive(world, pos)) {
                return blockState.setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
            }
        }
        return null;
    }

    public static Half getPlacementHeight(LevelReader world, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) return Half.TOP;

        BlockState placeOnState = world.getBlockState(pos);
        if (!Shapes.joinIsNotEmpty(placeOnState.getBlockSupportShape(world, pos).getFaceShape(direction), ATTACHABLE_HIGH, BooleanOp.ONLY_SECOND)) return Half.TOP;
        if (!Shapes.joinIsNotEmpty(placeOnState.getBlockSupportShape(world, pos).getFaceShape(direction), ATTACHABLE_LOW, BooleanOp.ONLY_SECOND)) return Half.BOTTOM;
        return Half.TOP;
    }



    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HALF).add(WATERLOGGED).add(END);
    }

    static {
        WEST_SHAPE = Shapes.or(
                Block.box(5.0D, 0.0D, 7.0D, 7.0D, 12.0D, 9.0D),
                Block.box(4.0D, 12.0D, 7.0D, 8.0D, 16.0D, 9.0D)
        );
        EAST_SHAPE = Shapes.or(
                Block.box(9.0D, 0.0D, 7.0D, 11.0D, 12.0D, 9.0D),
                Block.box(8.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D)
        );
        NORTH_SHAPE = Shapes.or(
                Block.box(7.0D, 0.0D, 5.0D, 9.0D, 12.0D, 7.0D),
                Block.box(7.0D, 12.0D, 4.0D, 9.0D, 16.0D, 8.0D)
        );
        SOUTH_SHAPE = Shapes.or(
                Block.box(7.0D, 0.0D, 9.0D, 9.0D, 12.0D, 11.0D),
                Block.box(7.0D, 12.0D, 8.0D, 9.0D, 16.0D, 12.0D)
        );
        WEST_BOTTOM_SHAPE = Shapes.or(
                Block.box(5.0D, 0.0D, 7.0D, 7.0D, 4.0D, 9.0D),
                Block.box(4.0D, 4.0D, 7.0D, 8.0D, 8.0D, 9.0D)
        );
        EAST_BOTTOM_SHAPE = Shapes.or(
                Block.box(9.0D, 0.0D, 7.0D, 11.0D, 4.0D, 9.0D),
                Block.box(8.0D, 4.0D, 7.0D, 12.0D, 8.0D, 9.0D)
        );
        NORTH_BOTTOM_SHAPE = Shapes.or(
                Block.box(7.0D, 0.0D, 5.0D, 9.0D, 4.0D, 7.0D),
                Block.box(7.0D, 4.0D, 4.0D, 9.0D, 8.0D, 8.0D)
        );
        SOUTH_BOTTOM_SHAPE = Shapes.or(
                Block.box(7.0D, 0.0D, 9.0D, 9.0D, 4.0D, 11.0D),
                Block.box(7.0D, 4.0D, 8.0D, 9.0D, 8.0D, 12.0D)
        );
        UP_SHAPE = Shapes.or(
                Block.box(7.0D, 0.0D, 7.0D, 9.0D, 8.0D, 9.0D),
                Block.box(7.0D, 8.0D, 6.0D, 9.0D, 12.0D, 10.0D)
        );
        ATTACHABLE_HIGH = Shapes.join(
                Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D),
                BooleanOp.ONLY_FIRST
        );
        ATTACHABLE_LOW = Shapes.join(
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
                BooleanOp.ONLY_FIRST
        );
    }
}

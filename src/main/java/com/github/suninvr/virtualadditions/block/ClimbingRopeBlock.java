package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ClimbingRopeBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<ClimbingRopeBlock> CODEC = simpleCodec(ClimbingRopeBlock::new);
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty END = BooleanProperty.create("end");
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape UP_SHAPE;

    public ClimbingRopeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.UP)
                .setValue(WATERLOGGED, false)
                .setValue(END, true)
        );
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> UP_SHAPE;
        };
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (state.canSurvive(world, pos)) {
            BlockPos belowPos = new BlockPos(pos.below());
            BlockState belowState = world.getBlockState(belowPos);
            if (world.getMinY() <= belowPos.getY() && (belowState.canBeReplaced(new DirectionalPlaceContext(world, belowPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) && !belowState.is(Blocks.LAVA) && !belowState.is(VABlocks.ACID)) {
                BlockState newState = state.setValue(WATERLOGGED, world.getFluidState(belowPos).getType() == Fluids.WATER);
                world.setBlockAndUpdate(belowPos, newState);
                world.setBlockAndUpdate(pos, state.setValue(END, false));
                world.playSound(null, belowPos, VASoundEvents.BLOCK_ROPE_EXTEND, SoundSource.BLOCKS, 0.33F, 1.2F);
                world.scheduleTick(belowPos, VABlocks.CLIMBING_ROPE, 1);
                world.gameEvent(null, GameEvent.BLOCK_PLACE, belowPos);
            }
        } else {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        if (!state.canSurvive(world, pos)) world.scheduleTick(pos, VABlocks.CLIMBING_ROPE, 1);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.above()).is(VABlockTags.CLIMBING_ROPES) && (world.getBlockState(new BlockPos(pos.below())).is(VABlockTags.CLIMBING_ROPES) || state.getValue(END));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean bl) {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED).add(END);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!state.getValue(END) && stack.is(Items.SHEARS)) {
            world.setBlockAndUpdate(pos, state.setValue(END, true));
            if (world.getBlockState(pos.below()).is(VABlocks.CLIMBING_ROPE)) world.destroyBlock(pos.below(), true, player);
            stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
            world.playSound(player, pos, SoundEvents.SHEARS_SNIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    static {
        WEST_SHAPE = Block.box(5.0D, 0.0D, 7.0D, 7.0D, 16.0D, 9.0D);
        EAST_SHAPE = Block.box(9.0D, 0.0D, 7.0D, 11.0D, 16.0D, 9.0D);
        NORTH_SHAPE = Block.box(7.0D, 0.0D, 5.0D, 9.0D, 16.0D, 7.0D);
        SOUTH_SHAPE = Block.box(7.0D, 0.0D, 9.0D, 9.0D, 16.0D, 11.0D);
        UP_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    }
}

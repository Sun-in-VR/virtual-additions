package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ClimbingRopeBlock extends Block implements Waterloggable {
    public static final MapCodec<ClimbingRopeBlock> CODEC = createCodec(ClimbingRopeBlock::new);
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty END = BooleanProperty.of("end");
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape UP_SHAPE;

    public ClimbingRopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.UP)
                .with(WATERLOGGED, false)
                .with(END, true)
        );
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> UP_SHAPE;
        };
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.canPlaceAt(world, pos)) {
            BlockPos belowPos = new BlockPos(pos.down());
            BlockState belowState = world.getBlockState(belowPos);
            if (world.getBottomY() <= belowPos.getY() && (belowState.canReplace(new AutomaticItemPlacementContext(world, belowPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) && !belowState.isOf(Blocks.LAVA) && !belowState.isOf(VABlocks.ACID)) {
                BlockState newState = state.with(WATERLOGGED, world.getFluidState(belowPos).getFluid() == Fluids.WATER);
                world.setBlockState(belowPos, newState);
                world.setBlockState(pos, state.with(END, false));
                world.playSound(null, belowPos, VASoundEvents.BLOCK_ROPE_EXTEND, SoundCategory.BLOCKS, 0.33F, 1.2F);
                world.scheduleBlockTick(belowPos, VABlocks.CLIMBING_ROPE, 1);
                world.emitGameEvent(null, GameEvent.BLOCK_PLACE, belowPos);
            }
        } else {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!state.canPlaceAt(world, pos)) world.scheduleBlockTick(pos, VABlocks.CLIMBING_ROPE, 1);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isIn(VABlockTags.CLIMBING_ROPES) && (world.getBlockState(new BlockPos(pos.down())).isIn(VABlockTags.CLIMBING_ROPES) || state.get(END));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean bl) {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED).add(END);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(END) && stack.isOf(Items.SHEARS)) {
            world.setBlockState(pos, state.with(END, true));
            if (world.getBlockState(pos.down()).isOf(VABlocks.CLIMBING_ROPE)) world.breakBlock(pos.down(), true, player);
            stack.damage(1, player, hand.getEquipmentSlot());
            world.playSound(player, pos, SoundEvents.ITEM_SHEARS_SNIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, stack);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    static {
        WEST_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 7.0D, 7.0D, 16.0D, 9.0D);
        EAST_SHAPE = Block.createCuboidShape(9.0D, 0.0D, 7.0D, 11.0D, 16.0D, 9.0D);
        NORTH_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 5.0D, 9.0D, 16.0D, 7.0D);
        SOUTH_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 9.0D, 9.0D, 16.0D, 11.0D);
        UP_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    }
}

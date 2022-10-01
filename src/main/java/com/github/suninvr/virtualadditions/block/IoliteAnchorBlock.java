package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.IoliteAnchorBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class IoliteAnchorBlock extends BlockWithEntity implements Waterloggable {
    public IoliteAnchorBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(POWERED, false)
                .with(CEILING, false)
                .with(WATERLOGGED, false)
        );
    }
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty CEILING = BooleanProperty.of("ceiling");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape HITBOX;
    private static final VoxelShape HITBOX_CEILING;


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED).add(CEILING).add(WATERLOGGED);
    }

    //On use: if the player is holding the Tether block, set its data appropriately.
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);

        //Check the Item stack
        if (stack.isOf(VAItems.IOLITE_TETHER)) {
            NbtCompound tag = stack.getOrCreateNbt();
            tag.put("destination", NbtHelper.fromBlockPos(pos));
            world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 0.6F);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    //Update redstone power on neighbor update.
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        if (!world.isClient) {
            boolean bl = world.isReceivingRedstonePower(pos);
            if(bl) world.setBlockState(pos, state.with(POWERED, true));
            else world.setBlockState(pos, state.with(POWERED, false));
        }
    }

    //Appearance & Shape

    //Define the voxel shape. This is the block's hit box.
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return state.get(CEILING) ? HITBOX_CEILING : HITBOX;
    }

    //Water logging

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(CEILING, ctx.getSide().equals(Direction.DOWN)).with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IoliteAnchorBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, VABlockEntities.IOLITE_ANCHOR_BLOCK_ENTITY, IoliteAnchorBlockEntity::tick);
    }

    static {
        HITBOX = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.375, 1f);
        HITBOX_CEILING = VoxelShapes.cuboid(0f, 0.625f, 0f, 1f, 1f, 1f);
    }
}

package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.IoliteTetherBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class IoliteTetherBlock extends BlockWithEntity implements Waterloggable {
    //Setup

    public static BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");
    public static BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    private static final VoxelShape SHAPE;

    //Block entity and its tag.
    IoliteTetherBlockEntity blockEntity;

    public IoliteTetherBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(COOLDOWN, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN).add(WATERLOGGED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IoliteTetherBlockEntity(pos, state);
    }

    //Copy NBT from the item once placed.
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        blockEntity = (IoliteTetherBlockEntity) world.getBlockEntity(pos);
        if (blockEntity == null) return;
        blockEntity.readNbt(itemStack.getOrCreateNbt());
        blockEntity.markDirty();

    }

    //Initiate the teleport once an entity steps on the block
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!world.isClient() && !world.getBlockState(pos).get(COOLDOWN) && !entity.isSneaking()) {
            teleportEntity(world, pos, entity);
        }
    }

    //Teleport Process.
    public void teleportEntity(World world, BlockPos pos, Entity entity) {
        if (!world.isClient()){
            // Only activate if we are allowed to by vanilla portal logic
            if (entity.hasVehicle() || entity.hasPassengers() || !entity.canUsePortals()) return;
            NbtCompound blockEntityTag = getBlockEntityNbt(world, pos);
            if (blockEntityTag == null) return;
            BlockPos destPos = NbtHelper.toBlockPos(blockEntityTag.getCompound("destination"));
            float destX = (float) (destPos.getX() + (entity.getX() - pos.getX()) - 0.5);
            float destY = (float) (destPos.getY() + (entity.getY() - pos.getY()) - 0.375);
            float destZ = (float) (destPos.getZ() + (entity.getZ() - pos.getZ()) - 0.5);
            double offsetY = 0;
            boolean ceiling;
            boolean success = false;
            BlockState block = world.getBlockState(destPos);

            if (pos.getSquaredDistance(destPos) <= 65536 && block.getBlock() == VABlocks.IOLITE_ANCHOR) {
                ceiling = block.get(IoliteAnchorBlock.CEILING);
                offsetY = ceiling ? (entity.getBoundingBox().getYLength()) * -1 + 0.375: 0.375;
                success = !block.get(IoliteAnchorBlock.POWERED) && world.isSpaceEmpty(entity.getType().createSimpleBoundingBox(destX + 0.5, (destY + offsetY), destZ + 0.5));
            }
            if (success) {
                destX += 0.5;
                destY += offsetY;
                destZ += 0.5;
                entity.teleport(destX, destY, destZ);

                if (!block.get(Properties.WATERLOGGED)) {
                    world.playSound(null, destPos, VASoundEvents.BLOCK_IOLITE_ANCHOR_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                if (!world.getBlockState(pos).get(WATERLOGGED)) {
                    world.playSound(null, pos, VASoundEvents.BLOCK_IOLITE_TETHER_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            } else if (!world.getBlockState(pos).get(WATERLOGGED)) world.playSound(null, pos, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.8F);

            world.setBlockState(pos, world.getBlockState(pos).with(COOLDOWN, true));
            world.createAndScheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 20);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(COOLDOWN)) {
            world.setBlockState(pos, state.with(COOLDOWN, false));
            if (!state.get(WATERLOGGED)) world.playSound(null, pos, SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.6F);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity playerEntity) {
            if (!world.isClient) {
                NbtCompound blockEntityTag = getBlockEntityNbt(world, pos);
                if (!(blockEntityTag == null)) {
                    ItemStack itemStack = VAItems.IOLITE_TETHER.getDefaultStack();
                    NbtCompound tag = itemStack.getOrCreateNbt();
                    tag.put("destination", blockEntityTag.get("destination"));

                    ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        super.onBreak(world, pos, state, playerEntity);
    }

    @Nullable
    protected NbtCompound getBlockEntityNbt(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) return getBlockEntityNbt(blockEntity);
        return null;
    }
    @Nullable
    protected NbtCompound getBlockEntityNbt(BlockEntity blockEntity) {
        if (blockEntity instanceof IoliteTetherBlockEntity ioliteTetherBlockEntity) {
            NbtCompound blockEntityTag = new NbtCompound();
            ioliteTetherBlockEntity.writeNbt(blockEntityTag);
            return blockEntityTag;
        }
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, VABlockEntities.IOLITE_TETHER_BLOCK_ENTITY, IoliteTetherBlockEntity::tick);
    }

    //Waterlogged State
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {return BlockRenderType.MODEL;}

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    static {
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.375, 1f);
    }

}

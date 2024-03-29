package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.WarpTetherBlockEntity;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WarpTetherBlock extends BlockWithEntity implements Waterloggable {
    public static BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");
    public static BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    private static final VoxelShape SHAPE;

    public WarpTetherBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(COOLDOWN, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN).add(WATERLOGGED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WarpTetherBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        WarpTetherBlockEntity blockEntity = (WarpTetherBlockEntity) world.getBlockEntity(pos);
        if (blockEntity == null) return;
        blockEntity.readNbt(itemStack.getOrCreateNbt());
        blockEntity.markDirty();

    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!world.isClient() && !state.get(COOLDOWN) && !entity.isSneaking() && entity.canUsePortals()) {
            teleportEntity(world, state, pos, entity);
        }
    }

    public void teleportEntity(World world, BlockState state, BlockPos pos, Entity entity) {
        if (!world.isClient()){
            NbtCompound blockEntityTag = getBlockEntityNbt(world, pos);
            if (blockEntityTag == null) return;
            BlockPos destPos = NbtHelper.toBlockPos(blockEntityTag.getCompound("destination"));
            BlockState destState = world.getBlockState(destPos);
            double squaredDistance = pos.getSquaredDistance(destPos);
            double destX = (destPos.getX() + (entity.getX() - pos.getX()));
            double destY = (destPos.getY() + (entity.getY() - pos.getY()));
            double destZ = (destPos.getZ() + (entity.getZ() - pos.getZ()));
            boolean bl = entity instanceof LivingEntity livingEntity && livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE) != null;
            if (!bl && destState.getBlock() == VABlocks.WARP_ANCHOR && !destState.get(WarpAnchorBlock.POWERED)) {
                destY += destState.get(WarpAnchorBlock.FACING).equals(Direction.DOWN) ? (entity.getBoundingBox().getYLength()) * -1 : 0;
                entity.teleport(destX, destY, destZ);
                world.emitGameEvent(entity, GameEvent.TELEPORT, pos);
                world.emitGameEvent(entity, GameEvent.TELEPORT, destPos);
                if (!state.get(WATERLOGGED)) world.playSound(null, pos, VASoundEvents.BLOCK_WARP_TETHER_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!destState.get(Properties.WATERLOGGED)) world.playSound(null, destPos, VASoundEvents.BLOCK_WARP_ANCHOR_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (world.getGameRules().getBoolean(VAGameRules.IOLITE_INTERFERENCE) && entity instanceof LivingEntity livingEntity) {
                    int duration = 0;
                    StatusEffectInstance effect = livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE);
                    if (effect != null) duration = effect.getDuration();
                    duration = Math.min((int)Math.max(600 * Math.sqrt(squaredDistance) / 256, duration), 72000);
                    livingEntity.addStatusEffect(new StatusEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, duration, 0, true, true));
                }
                if (entity instanceof ServerPlayerEntity player) {
                    VAAdvancementCriteria.USE_TELEPORTER.trigger(player);
                }
            } else if (!state.get(WATERLOGGED)) world.playSound(null, pos, VASoundEvents.BLOCK_WARP_TETHER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.8F);
            world.setBlockState(pos, state.with(COOLDOWN, true));
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(COOLDOWN)) {
            world.setBlockState(pos, state.with(COOLDOWN, false));
            if (!state.get(WATERLOGGED)) world.playSound(null, pos, VASoundEvents.BLOCK_WARP_TETHER_RECHARGE, SoundCategory.BLOCKS, 1.0F, 1.6F);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity playerEntity) {
        if (!world.isClient) {
            NbtCompound blockEntityTag = getBlockEntityNbt(world, pos);
            if (blockEntityTag != null) {
                ItemStack itemStack = VAItems.WARP_TETHER.getDefaultStack();
                itemStack.getOrCreateNbt().put("destination", blockEntityTag.get("destination"));
                dropStack(world, pos, itemStack);
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
        if (blockEntity instanceof WarpTetherBlockEntity warpTetherBlockEntity) {
            NbtCompound blockEntityTag = new NbtCompound();
            warpTetherBlockEntity.writeNbt(blockEntityTag);
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
        return checkType(type, VABlockEntities.IOLITE_TETHER_BLOCK_ENTITY, WarpTetherBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    static {
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.375, 1f);
    }

}

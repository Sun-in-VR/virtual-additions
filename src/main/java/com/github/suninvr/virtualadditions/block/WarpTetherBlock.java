package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.WarpTetherBlockEntity;
import com.github.suninvr.virtualadditions.component.WarpTetherLocationComponent;
import com.github.suninvr.virtualadditions.registry.*;
import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WarpTetherBlock extends BlockWithEntity implements Waterloggable {
    public static final MapCodec<WarpTetherBlock> CODEC = createCodec(WarpTetherBlock::new);
    public static BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");
    public static BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    private static final VoxelShape SHAPE;

    public WarpTetherBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(COOLDOWN, false).with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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
        WarpTetherLocationComponent component = itemStack.get(VADataComponentTypes.WARP_TETHER_LOCATION);
        if (component != null) {
            component.pos().ifPresent(blockEntity::setDestination);
        }
        blockEntity.markDirty();

    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!world.isClient() && !state.get(COOLDOWN) && !entity.isSneaking()) {
            teleportEntity(world, state, pos, entity);
        }
    }

    public void teleportEntity(World world, BlockState state, BlockPos pos, Entity entity) {
        if (!world.isClient()){
            BlockPos destPos = getBlockDestination(world, pos);
            if (destPos == null) return;
            BlockState destState = world.getBlockState(destPos);
            double squaredDistance = pos.getSquaredDistance(destPos);
            double destX = (destPos.getX() + (entity.getX() - pos.getX()));
            double destY = (destPos.getY() + (entity.getY() - pos.getY()));
            double destZ = (destPos.getZ() + (entity.getZ() - pos.getZ()));
            boolean bl = entity instanceof LivingEntity livingEntity && livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE) != null;
            if (!bl && destState.getBlock() == VABlocks.WARP_ANCHOR && !destState.get(WarpAnchorBlock.POWERED)) {
                destY += destState.get(WarpAnchorBlock.FACING).equals(Direction.DOWN) ? (entity.getBoundingBox().getLengthY()) * -1 : 0;
                entity.requestTeleport(destX, destY, destZ);
                world.emitGameEvent(entity, GameEvent.TELEPORT, pos);
                world.emitGameEvent(entity, GameEvent.TELEPORT, destPos);
                if (!state.get(WATERLOGGED)) world.playSound(null, pos, VASoundEvents.BLOCK_WARP_TETHER_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!destState.get(Properties.WATERLOGGED)) world.playSound(null, destPos, VASoundEvents.BLOCK_WARP_ANCHOR_WARP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (world.getGameRules().getBoolean(VAGameRules.IOLITE_INTERFERENCE) && entity instanceof LivingEntity livingEntity) {
                    int duration = 0;
                    StatusEffectInstance effect = livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE);
                    if (effect != null) duration = effect.getDuration();
                    duration = Math.min((int)Math.max(600 * Math.sqrt(squaredDistance) / 256, duration), 72000);
                    livingEntity.addStatusEffect(new StatusEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, duration, 0, false, true));
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity playerEntity) {
        if (!world.isClient) {
            BlockPos destination = getBlockDestination(world, pos);
            if (destination != null) {
                ItemStack stack = VAItems.WARP_TETHER.getDefaultStack();
                stack.set(VADataComponentTypes.WARP_TETHER_LOCATION, new WarpTetherLocationComponent(destination));
                dropStack(world, pos, stack);
            }
        }
        return super.onBreak(world, pos, state, playerEntity);
    }

    @Nullable
    protected BlockPos getBlockDestination(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof WarpTetherBlockEntity tetherBlockEntity) return tetherBlockEntity.getDestination();
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, VABlockEntityType.WARP_TETHER, WarpTetherBlockEntity::tick);
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
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    static {
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.375, 1f);
    }

}

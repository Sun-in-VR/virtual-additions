package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.MiniPortalState;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class MiniPortalBlock extends BlockWithEntity implements Waterloggable {
    public static final MapCodec<MiniPortalBlock> CODEC = createCodec(MiniPortalBlock::new);
    private final VoxelShape SHAPE = createColumnShape(10, 3, 13);
    private final Box BOX = VoxelShapes.fullCube().getBoundingBox();
    public static final EnumProperty<MiniPortalState> STATE = EnumProperty.of("state", MiniPortalState.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public MiniPortalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(STATE, MiniPortalState.OPEN)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STATE, WATERLOGGED);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof DyeItem dyeItem && world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity && entity.setDyeColor(dyeItem.getColor())) {
            stack.decrementUnlessCreative(1, player);
            world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(Items.POTION) && stack.contains(DataComponentTypes.POTION_CONTENTS) && stack.get(DataComponentTypes.POTION_CONTENTS).matches(Potions.WATER)&& world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity && entity.setDyeColor(null)) {
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MiniPortalBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Vec3d particlePos = pos.toCenterPos().addRandom(random, 1);
        if (world.getBlockEntity(pos) instanceof MiniPortalBlockEntity miniPortalBlockEntity) world.addParticleClient(miniPortalBlockEntity.getParticleParameter(), particlePos.x, particlePos.y, particlePos.z, 0, 0,0);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (state.get(STATE).equals(MiniPortalState.POWERED)) return;
        Optional<BlockPos> destination = getDestination(world, pos);
        if (destination.isPresent() && state.get(STATE).canDepart) {
            MiniPortalBlockEntity blockEntity = (MiniPortalBlockEntity) world.getBlockEntity(pos);
            BlockState destState = world.getBlockState(destination.get());
            if (!destState.isOf(this)) return;
            if (destState.get(STATE).canArrive && canTeleportEntity(entity)) {
                world.setBlockState(pos, state.with(STATE, MiniPortalState.COOLDOWN));
                world.setBlockState(destination.get(), destState.with(STATE, MiniPortalState.COOLDOWN));
                world.scheduleBlockTick(pos, this, 20);
                world.scheduleBlockTick(destination.get(), this, 20);
                teleportEntity(world, pos, destination.get(), entity, blockEntity.getParticleParameter());
            } else {
                world.playSound(null, pos, VASoundEvents.BLOCK_MINI_PORTAL_FAIL, SoundCategory.BLOCKS, 1.0F, 1.6F);
                world.setBlockState(pos, state.with(STATE, MiniPortalState.BLOCKED));
                world.scheduleBlockTick(pos, this, 20);
            }
        }
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return state.get(STATE).equals(MiniPortalState.COOLDOWN) ? 15 : 0;
    }

    protected boolean canTeleportEntity(Entity entity) {
        if (((EntityInterface)entity).virtualAdditions$hasUsedMiniPortalThisTick()) return false;
        return !(entity instanceof LivingEntity livingEntity) || livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE) == null;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (state.get(STATE).equals(MiniPortalState.POWERED)) return;
        int entityCount = getEntityCount(world, BOX.offset(pos));
        if (entityCount > 0) {
            if (!state.get(STATE).equals(MiniPortalState.BLOCKED)) world.setBlockState(pos, state.with(STATE, MiniPortalState.BLOCKED));
            world.scheduleBlockTick(pos, this, 20);
        } else {
            world.playSound(null, pos, VASoundEvents.BLOCK_MINI_PORTAL_RECHARGE, SoundCategory.BLOCKS, 1.0F, 1.6F);
            world.setBlockState(pos, state.with(STATE, MiniPortalState.OPEN));
        }
    }

    public void teleportEntity(World world, BlockPos origin, BlockPos destination, Entity entity, ParticleEffect particleEffect) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld){
            if (destination == null) return;
            double squaredDistance = origin.getSquaredDistance(destination);
            double destX = (destination.getX() + (entity.getX() - origin.getX()));
            double destY = (destination.getY() + (entity.getY() - origin.getY()));
            double destZ = (destination.getZ() + (entity.getZ() - origin.getZ()));
            entity.requestTeleport(destX, destY, destZ);
            ((EntityInterface)entity).virtualAdditions$setUsedMiniPortal();
            int i = (int) Math.clamp((entity.getWidth() * entity.getWidth() * entity.getHeight() * 20.0F), 5, 50);
            ((ServerWorld) world).spawnParticles(particleEffect, destX, destY, destZ, i, entity.getWidth() * 0.45, entity.getHeight() * 0.25, entity.getWidth() * 0.45, 0);
            world.emitGameEvent(entity, GameEvent.TELEPORT, origin);
            world.emitGameEvent(entity, GameEvent.TELEPORT, destination);
            world.playSound(null, origin, VASoundEvents.BLOCK_MINI_PORTAL_DEPART, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.playSound(null, destination, VASoundEvents.BLOCK_MINI_PORTAL_ARRIVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (serverWorld.getGameRules().getBoolean(VAGameRules.IOLITE_INTERFERENCE) && entity instanceof LivingEntity livingEntity && !livingEntity.isInCreativeMode()) {
                int duration = 0;
                StatusEffectInstance effect = livingEntity.getStatusEffect(VAStatusEffects.IOLITE_INTERFERENCE);
                if (effect != null) duration = effect.getDuration();
                duration = Math.min((int)Math.max(600 * Math.sqrt(squaredDistance) / 128, duration), Integer.MAX_VALUE);
                livingEntity.addStatusEffect(new StatusEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, duration, 0, false, true));
            }
            if (entity instanceof ServerPlayerEntity player) {
                VAAdvancementCriteria.USE_TELEPORTER.trigger(player);
            }
        }
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return VAItems.PORTAL_CORE.getDefaultStack();
    }

    protected static int getEntityCount(World world, Box box) {
        return world.getEntitiesByClass(Entity.class, box, EntityPredicates.EXCEPT_SPECTATOR).size();
    }

    protected static Optional<BlockPos> getDestination(World world, BlockPos pos) {
        BlockPos[] dest = {null};
        ifBlockEntity(world, pos, entity -> dest[0] = entity.getDestination());
        return Optional.ofNullable(dest[0]);
    }

    protected static void ifBlockEntity(World world, BlockPos pos, Consumer<MiniPortalBlockEntity> consumer) {
        world.getBlockEntity(pos, VABlockEntityType.MINI_PORTAL).ifPresent(consumer);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return super.getPlacementState(ctx).with(WATERLOGGED, fluidState.isOf(Fluids.WATER));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (world.isReceivingRedstonePower(pos) && !state.get(STATE).equals(MiniPortalState.POWERED)) {
            world.setBlockState(pos, state.with(STATE, MiniPortalState.POWERED));
        } else if (!world.isReceivingRedstonePower(pos) && state.get(STATE).equals(MiniPortalState.POWERED)) {
            world.setBlockState(pos, state.with(STATE, MiniPortalState.OPEN));
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }
}

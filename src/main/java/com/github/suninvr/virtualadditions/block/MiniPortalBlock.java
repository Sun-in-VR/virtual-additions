package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.MiniPortalState;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class MiniPortalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<MiniPortalBlock> CODEC = simpleCodec(MiniPortalBlock::new);
    private final VoxelShape SHAPE = column(10, 3, 13);
    private final AABB BOX = Shapes.block().bounds();
    public static final EnumProperty<MiniPortalState> STATE = EnumProperty.create("state", MiniPortalState.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MiniPortalBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(STATE, MiniPortalState.OPEN)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE, WATERLOGGED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof DyeItem dyeItem && world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity && entity.setDyeColor(dyeItem.getDyeColor())) {
            stack.consume(1, player);
            world.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(Items.POTION) && stack.has(DataComponents.POTION_CONTENTS) && stack.get(DataComponents.POTION_CONTENTS).is(Potions.WATER)&& world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity && entity.setDyeColor(null)) {
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MiniPortalBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Vec3 particlePos = pos.getCenter().offsetRandom(random, 1);
        if (world.getBlockEntity(pos) instanceof MiniPortalBlockEntity miniPortalBlockEntity) world.addParticle(miniPortalBlockEntity.getParticleParameter(), particlePos.x, particlePos.y, particlePos.z, 0, 0,0);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl) {
        if (state.getValue(STATE).equals(MiniPortalState.POWERED)) return;
        Optional<BlockPos> destination = getDestination(world, pos);
        if (destination.isPresent() && state.getValue(STATE).canDepart) {
            MiniPortalBlockEntity blockEntity = (MiniPortalBlockEntity) world.getBlockEntity(pos);
            BlockState destState = world.getBlockState(destination.get());
            if (!destState.is(this)) return;
            if (destState.getValue(STATE).canArrive && canTeleportEntity(entity)) {
                world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.COOLDOWN));
                world.setBlockAndUpdate(destination.get(), destState.setValue(STATE, MiniPortalState.COOLDOWN));
                world.scheduleTick(pos, this, 20);
                world.scheduleTick(destination.get(), this, 20);
                teleportEntity(world, pos, destination.get(), entity, blockEntity.getParticleParameter());
            } else {
                world.playSound(null, pos, VASoundEvents.BLOCK_MINI_PORTAL_FAIL, SoundSource.BLOCKS, 1.0F, 1.6F);
                world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.BLOCKED));
                world.scheduleTick(pos, this, 20);
            }
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
        return state.getValue(STATE).equals(MiniPortalState.COOLDOWN) ? 15 : 0;
    }

    protected boolean canTeleportEntity(Entity entity) {
        if (((EntityInterface)entity).virtualAdditions$hasUsedMiniPortalThisTick()) return false;
        return !(entity instanceof LivingEntity livingEntity) || livingEntity.getEffect(VAStatusEffects.IOLITE_INTERFERENCE) == null;
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        if (state.getValue(STATE).equals(MiniPortalState.POWERED)) return;
        int entityCount = getEntityCount(world, BOX.move(pos));
        if (entityCount > 0) {
            if (!state.getValue(STATE).equals(MiniPortalState.BLOCKED)) world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.BLOCKED));
            world.scheduleTick(pos, this, 20);
        } else {
            world.playSound(null, pos, VASoundEvents.BLOCK_MINI_PORTAL_RECHARGE, SoundSource.BLOCKS, 1.0F, 1.6F);
            world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.OPEN));
        }
    }

    public void teleportEntity(Level world, BlockPos origin, BlockPos destination, Entity entity, ParticleOptions particleEffect) {
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld){
            if (destination == null) return;
            double squaredDistance = origin.distSqr(destination);
            double destX = (destination.getX() + (entity.getX() - origin.getX()));
            double destY = (destination.getY() + (entity.getY() - origin.getY()));
            double destZ = (destination.getZ() + (entity.getZ() - origin.getZ()));
            entity.teleportTo(destX, destY, destZ);
            ((EntityInterface)entity).virtualAdditions$setUsedMiniPortal();
            int i = (int) Math.clamp((entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() * 20.0F), 5, 50);
            ((ServerLevel) world).sendParticles(particleEffect, destX, destY, destZ, i, entity.getBbWidth() * 0.45, entity.getBbHeight() * 0.25, entity.getBbWidth() * 0.45, 0);
            world.gameEvent(entity, GameEvent.TELEPORT, origin);
            world.gameEvent(entity, GameEvent.TELEPORT, destination);
            world.playSound(null, origin, VASoundEvents.BLOCK_MINI_PORTAL_DEPART, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.playSound(null, destination, VASoundEvents.BLOCK_MINI_PORTAL_ARRIVE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (serverWorld.getGameRules().get(VAGameRules.IOLITE_INTERFERENCE) && entity instanceof LivingEntity livingEntity && !livingEntity.hasInfiniteMaterials()) {
                int duration = 0;
                MobEffectInstance effect = livingEntity.getEffect(VAStatusEffects.IOLITE_INTERFERENCE);
                if (effect != null) duration = effect.getDuration();
                duration = Math.min((int)Math.max(600 * Math.sqrt(squaredDistance) / 128, duration), Integer.MAX_VALUE);
                livingEntity.addEffect(new MobEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, duration, 0, false, true));
            }
            if (entity instanceof ServerPlayer player) {
                VAAdvancementCriteria.USE_TELEPORTER.trigger(player);
            }
        }
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
        return VAItems.PORTAL_CORE.getDefaultInstance();
    }

    protected static int getEntityCount(Level world, AABB box) {
        return world.getEntitiesOfClass(Entity.class, box, EntitySelector.NO_SPECTATORS).size();
    }

    protected static Optional<BlockPos> getDestination(Level world, BlockPos pos) {
        BlockPos[] dest = {null};
        ifBlockEntity(world, pos, entity -> dest[0] = entity.getDestination());
        return Optional.ofNullable(dest[0]);
    }

    protected static void ifBlockEntity(Level world, BlockPos pos, Consumer<MiniPortalBlockEntity> consumer) {
        world.getBlockEntity(pos, VABlockEntityType.MINI_PORTAL).ifPresent(consumer);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return super.getStateForPlacement(ctx).setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        if (world.hasNeighborSignal(pos) && !state.getValue(STATE).equals(MiniPortalState.POWERED)) {
            world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.POWERED));
        } else if (!world.hasNeighborSignal(pos) && state.getValue(STATE).equals(MiniPortalState.POWERED)) {
            world.setBlockAndUpdate(pos, state.setValue(STATE, MiniPortalState.OPEN));
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}

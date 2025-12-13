package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ClimbingRopeEntity extends AbstractArrow {
    private static final EntityDataAccessor<Integer> OXIDATION_LEVEL = SynchedEntityData.defineId(ClimbingRopeEntity.class, EntityDataSerializers.INT);

    public ClimbingRopeEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }

    public ClimbingRopeEntity(double x, double y, double z, Level world, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world, stack, shotFrom);

        if (stack.is(VAItems.OXIDIZED_CLIMBING_ROPE) || stack.is(VAItems.WAXED_OXIDIZED_CLIMBING_ROPE)) this.setOxidation(WeatheringCopper.WeatherState.OXIDIZED);
        else if (stack.is(VAItems.WEATHERED_CLIMBING_ROPE) || stack.is(VAItems.WAXED_WEATHERED_CLIMBING_ROPE)) this.setOxidation(WeatheringCopper.WeatherState.WEATHERED);
        else if (stack.is(VAItems.EXPOSED_CLIMBING_ROPE) || stack.is(VAItems.WAXED_EXPOSED_CLIMBING_ROPE)) this.setOxidation(WeatheringCopper.WeatherState.EXPOSED);
        else this.setOxidation(WeatheringCopper.WeatherState.UNAFFECTED);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide()) return;
        BlockState state = this.getRopeState(blockHitResult);
        if (state != null) {
            BlockPos placePos = new BlockPos(blockHitResult.getBlockPos().relative(blockHitResult.getDirection()));
            if (this.getOwner() instanceof ServerPlayer serverPlayerEntity) {
                VAAdvancementCriteria.FIRE_CLIMBING_ROPE_FROM_CROSSBOW.trigger(serverPlayerEntity);
            }
            this.level().setBlockAndUpdate(placePos, state);
            this.level().scheduleTick(placePos, state.getBlock(), 1);
            this.level().playSound(this, placePos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS,1.0F, 0.8F);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(OXIDATION_LEVEL, 0);
    }

    protected BlockState getRopeState(BlockHitResult result) {
        if (this.getPickupItemStackOrigin().has(DataComponents.CAN_PLACE_ON)) {
            BlockInWorld chachedPos = new BlockInWorld(this.level(), result.getBlockPos(), false);
            if (!this.getPickupItemStackOrigin().get(DataComponents.CAN_PLACE_ON).test(chachedPos)) return null;
        }
        Direction dir = result.getDirection();
        BlockPos pos = result.getBlockPos().relative(dir);
        if (dir != Direction.UP && this.getPickupItemStackOrigin().is(VAItemTags.CLIMBING_ROPES) && this.getPickupItemStackOrigin().getItem() instanceof BlockItem blockItem) {
            Half half = ClimbingRopeAnchorBlock.getPlacementHeight(this.level(), result.getBlockPos(), result.getDirection());
            BlockState state = blockItem.getBlock().defaultBlockState().setValue(ClimbingRopeAnchorBlock.HALF, half).setValue(ClimbingRopeAnchorBlock.FACING, dir.getOpposite());
            if (this.level().getBlockState(pos).isAir() && ClimbingRopeAnchorBlock.canPlaceAt(blockItem.getBlock(), state, this.level(), pos)) {
                return state;
            }
        }
        return null;
    }

    public WeatheringCopper.WeatherState getOxidationLevel() {
        return switch (this.entityData.get(OXIDATION_LEVEL)) {
            case 3 -> WeatheringCopper.WeatherState.OXIDIZED;
            case 2 -> WeatheringCopper.WeatherState.WEATHERED;
            case 1 -> WeatheringCopper.WeatherState.EXPOSED;
            default -> WeatheringCopper.WeatherState.UNAFFECTED;
        };
    }

    public void setOxidation(WeatheringCopper.WeatherState level) {
        switch (level) {
            case UNAFFECTED -> this.entityData.set(OXIDATION_LEVEL, 0);
            case EXPOSED -> this.entityData.set(OXIDATION_LEVEL, 1);
            case WEATHERED -> this.entityData.set(OXIDATION_LEVEL, 2);
            case OXIDIZED -> this.entityData.set(OXIDATION_LEVEL, 3);
        }
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float divergence) {
        speed *= this.getSpeedMultipler();
        super.shoot(x, y, z, speed, 0);
    }

    @Override
    public void shootFromRotation(Entity shooter, float pitch, float yaw, float roll,  float speed, float divergence) {
        speed *= this.getSpeedMultipler();
        super.shootFromRotation(shooter, pitch, yaw, roll, speed, 0);
    }

    protected float getSpeedMultipler() {
        return switch (this.getOxidationLevel()) {
            case UNAFFECTED -> 1.0F;
            case EXPOSED -> 0.8F;
            case WEATHERED -> 0.6F;
            case OXIDIZED -> 0.4F;
        };
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return VASoundEvents.BLOCK_ROPE_HIT_GROUND;
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return false;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }
}

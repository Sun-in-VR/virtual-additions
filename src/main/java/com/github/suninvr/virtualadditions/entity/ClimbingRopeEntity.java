package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ClimbingRopeEntity extends PersistentProjectileEntity {
    private static final TrackedData<Integer> OXIDATION_LEVEL = DataTracker.registerData(ClimbingRopeEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ClimbingRopeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ClimbingRopeEntity(double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world, stack, shotFrom);

        if (stack.isOf(VAItems.OXIDIZED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_OXIDIZED_CLIMBING_ROPE)) this.setOxidation(Oxidizable.OxidationLevel.OXIDIZED);
        else if (stack.isOf(VAItems.WEATHERED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_WEATHERED_CLIMBING_ROPE)) this.setOxidation(Oxidizable.OxidationLevel.WEATHERED);
        else if (stack.isOf(VAItems.EXPOSED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_EXPOSED_CLIMBING_ROPE)) this.setOxidation(Oxidizable.OxidationLevel.EXPOSED);
        else this.setOxidation(Oxidizable.OxidationLevel.UNAFFECTED);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.getWorld().isClient) return;
        BlockState state = this.getRopeState(blockHitResult);
        if (state != null) {
            BlockPos placePos = new BlockPos(blockHitResult.getBlockPos().offset(blockHitResult.getSide()));
            this.getWorld().setBlockState(placePos, state);
            this.getWorld().scheduleBlockTick(placePos, state.getBlock(), 1);
            this.discard();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(OXIDATION_LEVEL, 0);
    }

    protected BlockState getRopeState(BlockHitResult result) {
        Direction dir = result.getSide();
        BlockPos pos = result.getBlockPos().offset(dir);
        if (dir != Direction.UP && this.getItemStack().isIn(VAItemTags.CLIMBING_ROPES) && this.getItemStack().getItem() instanceof BlockItem blockItem) {
            BlockHalf half = ClimbingRopeAnchorBlock.getPlacementHeight(this.getWorld(), result.getBlockPos(), result.getSide());
            BlockState state = blockItem.getBlock().getDefaultState().with(ClimbingRopeAnchorBlock.HALF, half).with(ClimbingRopeAnchorBlock.FACING, dir.getOpposite());
            if (this.getWorld().getBlockState(pos).isAir() && ClimbingRopeAnchorBlock.canPlaceAt(blockItem.getBlock(), state, this.getWorld(), pos)) {
                return state;
            }
        }
        return null;
    }

    public Oxidizable.OxidationLevel getOxidation() {
        return switch (this.dataTracker.get(OXIDATION_LEVEL)) {
            case 3 -> Oxidizable.OxidationLevel.OXIDIZED;
            case 2 -> Oxidizable.OxidationLevel.WEATHERED;
            case 1 -> Oxidizable.OxidationLevel.EXPOSED;
            default -> Oxidizable.OxidationLevel.UNAFFECTED;
        };
    }

    public void setOxidation(Oxidizable.OxidationLevel level) {
        switch (level) {
            case UNAFFECTED -> this.dataTracker.set(OXIDATION_LEVEL, 0);
            case EXPOSED -> this.dataTracker.set(OXIDATION_LEVEL, 1);
            case WEATHERED -> this.dataTracker.set(OXIDATION_LEVEL, 2);
            case OXIDIZED -> this.dataTracker.set(OXIDATION_LEVEL, 3);
        }
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        speed *= this.getSpeedMultipler();
        if (this.isShotFromCrossbow()) {
            divergence = 0;
        }
        super.setVelocity(x, y, z, speed, divergence);
    }

    @Override
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll,  float speed, float divergence) {
        speed *= this.getSpeedMultipler();
        if (this.isShotFromCrossbow()) {
            divergence = 0;
        }
        super.setVelocity(shooter, pitch, yaw, roll, speed, divergence);
    }

    protected float getSpeedMultipler() {
        return switch (this.getOxidation()) {
            case UNAFFECTED -> 1.0F;
            case EXPOSED -> 0.8F;
            case WEATHERED -> 0.6F;
            case OXIDIZED -> 0.4F;
        };
    }

    @Override
    protected boolean canHit(Entity entity) {
        return false;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }
}

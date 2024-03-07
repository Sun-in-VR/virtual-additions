package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ClimbingRopeEntity extends PersistentProjectileEntity {

    public ClimbingRopeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world, VAItems.CLIMBING_ROPE.getDefaultStack());
    }

    public ClimbingRopeEntity(double x, double y, double z, World world, ItemStack stack) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world, stack);
    }

    public ClimbingRopeEntity(double x, double y, double z, World world) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world, new ItemStack(VAItems.CLIMBING_ROPE));
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        Direction hitSide = blockHitResult.getSide();
        BlockPos placePos = new BlockPos(blockHitResult.getBlockPos().offset(hitSide));
        BlockHalf half = ClimbingRopeAnchorBlock.getPlacementHeight(this.getWorld(), blockHitResult.getBlockPos(), blockHitResult.getSide());
        if(hitSide != Direction.UP) {
            BlockState state = VABlocks.CLIMBING_ROPE_ANCHOR.getDefaultState().with(ClimbingRopeAnchorBlock.FACING, hitSide.getOpposite()).with(ClimbingRopeAnchorBlock.HALF, half);
            if( ClimbingRopeAnchorBlock.canPlaceAt( VABlocks.CLIMBING_ROPE_ANCHOR, state, this.getWorld(), placePos ) && this.getWorld().getBlockState( placePos ).isAir() ) {
                getWorld().setBlockState(placePos, state);
                this.getWorld().scheduleBlockTick(placePos, state.getBlock(), 1);
                this.discard();
            }
        }

    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        if (this.isShotFromCrossbow()) {
            speed *= 0.8F;
            divergence = 0;
        }
        super.setVelocity(x, y, z, speed, divergence);
    }

    @Override
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll,  float speed, float divergence) {
        if (this.isShotFromCrossbow()) {
            speed *= 0.8F;
            divergence = 0;
        }
        super.setVelocity(shooter, pitch, yaw, roll, speed, divergence);
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

package com.github.suninvr.virtualadditions.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;

public class ClimbingRopeEntity extends PersistentProjectileEntity {

    public ClimbingRopeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ClimbingRopeEntity(double x, double y, double z, World world) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world);
    }

    protected ClimbingRopeEntity(LivingEntity owner, World world) {
        super(VAEntityType.CLIMBING_ROPE, owner, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        Direction hitSide = blockHitResult.getSide();
        BlockPos placePos = blockHitResult.getBlockPos().offset(hitSide);
        if(hitSide != Direction.UP) {
            BlockState state = VABlocks.CLIMBING_ROPE_ANCHOR.getDefaultState().with(ClimbingRopeAnchorBlock.FACING, hitSide);
            if( VABlocks.CLIMBING_ROPE_ANCHOR.canPlaceAt( state, this.world, placePos ) && this.world.getBlockState( placePos ).isAir() ) {
                world.setBlockState(placePos, state);
                this.world.createAndScheduleBlockTick(placePos, state.getBlock(), 1);
                this.discard();
            }
        }

    }

    @Override
    protected boolean canHit(Entity entity) {
        return false;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(VAItems.CLIMBING_ROPE);
    }
}

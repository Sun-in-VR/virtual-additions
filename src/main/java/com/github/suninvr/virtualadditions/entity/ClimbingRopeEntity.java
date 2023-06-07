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

@SuppressWarnings("deprecation")
public class ClimbingRopeEntity extends PersistentProjectileEntity {

    public ClimbingRopeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ClimbingRopeEntity(double x, double y, double z, World world) {
        super(VAEntityType.CLIMBING_ROPE, x, y, z, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        Direction hitSide = blockHitResult.getSide();
        BlockPos placePos = new BlockPos(blockHitResult.getBlockPos().offset(hitSide));
        BlockHalf half = ClimbingRopeAnchorBlock.getPlacementHeight(this.getWorld(), blockHitResult.getBlockPos(), blockHitResult.getSide());
        if(hitSide != Direction.UP) {
            BlockState state = VABlocks.CLIMBING_ROPE_ANCHOR.getDefaultState().with(ClimbingRopeAnchorBlock.FACING, hitSide).with(ClimbingRopeAnchorBlock.HALF, half);
            if( VABlocks.CLIMBING_ROPE_ANCHOR.canPlaceAt( state, this.getWorld(), placePos ) && this.getWorld().getBlockState( placePos ).isAir() ) {
                getWorld().setBlockState(placePos, state);
                this.getWorld().scheduleBlockTick(placePos, state.getBlock(), 1);
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

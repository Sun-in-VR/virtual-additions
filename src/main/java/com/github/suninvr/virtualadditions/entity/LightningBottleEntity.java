package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningBottleEntity extends ThrownItemEntity {
    public LightningBottleEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public LightningBottleEntity(World world, LivingEntity owner, ItemStack stack) {
        super(VAEntityType.LIGHTNING_BOTTLE, owner, world, stack);
    }

    public LightningBottleEntity(World world, double x, double y, double z, ItemStack stack) {
        super(VAEntityType.LIGHTNING_BOTTLE, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return VAItems.LIGHTNING_BOTTLE;
    }

    protected double getGravity() {
        return 0.07;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld() instanceof ServerWorld) {
            this.getWorld().syncWorldEvent(2002, this.getBlockPos(), 7860223);
            Vec3d pos = hitResult.getPos();
            BlockPos blockPos = BlockPos.ofFloored(pos.x, pos.y, pos.z);
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
            if (this.getOwner() instanceof ServerPlayerEntity serverPlayerEntity) entity.setChanneler(serverPlayerEntity);
            entity.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            this.getWorld().spawnEntity(entity);
            this.discard();
        }

    }
}

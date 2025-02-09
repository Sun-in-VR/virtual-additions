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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Optional;

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
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.getWorld().syncWorldEvent(2002, this.getBlockPos(), 7860223);

            Optional<BlockPos> lightningRodPos = serverWorld.getPointOfInterestStorage()
                    .getNearestPosition(
                            poiType -> poiType.matchesKey(PointOfInterestTypes.LIGHTNING_ROD),
                            blockPos -> blockPos.getY() == serverWorld.getTopY(Heightmap.Type.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1,
                            BlockPos.ofFloored(hitResult.getPos()), 12, PointOfInterestStorage.OccupationStatus.ANY
                    );
            if (lightningRodPos.isPresent()) {
                this.summonLightningBolt(Vec3d.ofBottomCenter(lightningRodPos.get().up()));
            } else if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                this.summonLightningBolt(blockPos.toCenterPos().add(0.0, -0.5, 0.0));
            } else if (hitResult instanceof EntityHitResult entityHitResult) {
                this.summonLightningBolt(entityHitResult.getEntity().getPos());
            } else {
                this.summonLightningBolt(hitResult.getPos());
            }
            this.discard();
        }

    }

    private void summonLightningBolt(Vec3d pos) {
        LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
        if (this.getOwner() instanceof ServerPlayerEntity serverPlayerEntity) entity.setChanneler(serverPlayerEntity);
        entity.setPos(pos.x, pos.y, pos.z);
        this.getWorld().spawnEntity(entity);
    }
}

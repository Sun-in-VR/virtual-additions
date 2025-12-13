package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class LightningBottleEntity extends ThrowableItemProjectile {
    public LightningBottleEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public LightningBottleEntity(Level world, LivingEntity owner, ItemStack stack) {
        super(VAEntityType.LIGHTNING_BOTTLE, owner, world, stack);
    }

    public LightningBottleEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(VAEntityType.LIGHTNING_BOTTLE, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return VAItems.LIGHTNING_BOTTLE;
    }

    protected double getDefaultGravity() {
        return 0.07;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverWorld) {
            this.level().levelEvent(2002, this.blockPosition(), 7860223);

            Optional<BlockPos> lightningRodPos = serverWorld.getPoiManager()
                    .findClosest(
                            poiType -> poiType.is(PoiTypes.LIGHTNING_ROD),
                            blockPos -> blockPos.getY() == serverWorld.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1,
                            BlockPos.containing(hitResult.getLocation()), 12, PoiManager.Occupancy.ANY
                    );
            if (lightningRodPos.isPresent()) {
                this.summonLightningBolt(Vec3.atBottomCenterOf(lightningRodPos.get().above()));
            } else if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                this.summonLightningBolt(blockPos.getCenter().add(0.0, -0.5, 0.0));
            } else if (hitResult instanceof EntityHitResult entityHitResult) {
                this.summonLightningBolt(entityHitResult.getEntity().position());
            } else {
                this.summonLightningBolt(hitResult.getLocation());
            }
            this.discard();
        }

    }

    private void summonLightningBolt(Vec3 pos) {
        LightningBolt entity = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
        if (this.getOwner() instanceof ServerPlayer serverPlayerEntity) entity.setCause(serverPlayerEntity);
        entity.setPosRaw(pos.x, pos.y, pos.z);
        this.level().addFreshEntity(entity);
    }
}

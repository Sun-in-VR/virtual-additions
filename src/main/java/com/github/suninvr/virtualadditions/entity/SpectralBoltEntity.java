package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpectralBoltEntity extends Projectile {

    public SpectralBoltEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public SpectralBoltEntity(Level world, LivingEntity owner) {
        super(VAEntityType.SPECTRAL_BOLT, world);
        this.setNoGravity(true);
        this.setOwner(owner);
        this.setPos(owner.getX() - (double)(owner.getBbWidth() + 1.0F) * 0.5 * (double) Mth.sin(owner.yBodyRot * 0.017453292F), owner.getEyeY() - 0.10000000149011612, owner.getZ() + (double)(owner.getBbWidth() + 1.0F) * 0.5 * (double)Mth.cos(owner.yBodyRot * 0.017453292F));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle(VAParticleTypes.SPECTRAL_POWER, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        Vec3 vec3d = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.onHit(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        if (this.tickCount > 30 || this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else {
            if (vec3d.length() < 5) this.setDeltaMovement(vec3d.scale(1.1F));
            this.setPos(d, e, f);
        }
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof SpectreEntity) return;
        Entity entity = entityHitResult.getEntity();
        Level world = entity.level();
        if (world instanceof ServerLevel serverWorld) entity.hurtServer(serverWorld, this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0.5F + 0.5F * (world.getDifficulty().ordinal() - 1));
        this.discard();
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }

    }
}

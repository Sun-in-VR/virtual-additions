package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PebbleEntity extends Projectile {

    public PebbleEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    public PebbleEntity(double d, double e, double f, Level level) {
        super(VAEntityType.PEBBLE, level);
        this.setPos(d, e, f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void tick() {
        super.tick();
        Vec3 vec3d = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.onHit(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.99F;
        float h = -0.06F;
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isUnderWater()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3d.scale(g));
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, h, 0.0));
            }

            this.setPos(d, e, f);
        }
    }

    protected void land(@Nullable BlockState state) {
        if (this.level() == null || this.level().isClientSide()) return;
        if (state != null) ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), true, false, this.getX(), this.getY(), this.getZ(), 2, 0, 0, 0, 0.125);
        else ((ServerLevel) this.level()).sendParticles(ParticleTypes.CRIT, true, false, this.getX(), this.getY(), this.getZ(), 2, 0, 0, 0, 0.25);
        this.level().playSound(this, this.getX(), this.getY(), this.getZ(), SoundEvents.SPEAR_HIT.value(), this.getSoundSource(), 0.0125F, 2F);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 3.0F);
        this.land(null);
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            this.land(this.level().getBlockState(blockHitResult.getBlockPos()));
        }

    }
}

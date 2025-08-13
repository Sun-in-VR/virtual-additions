package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpectralBoltEntity extends ProjectileEntity {

    public SpectralBoltEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public SpectralBoltEntity(World world, LivingEntity owner) {
        super(VAEntityType.SPECTRAL_BOLT, world);
        this.setNoGravity(true);
        this.setOwner(owner);
        this.setPosition(owner.getX() - (double)(owner.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(owner.bodyYaw * 0.017453292F), owner.getEyeY() - 0.10000000149011612, owner.getZ() + (double)(owner.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * 0.017453292F));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
        super.tick();
        if (this.getEntityWorld().isClient()) {
            this.getEntityWorld().addParticleClient(VAParticleTypes.SPECTRAL_POWER, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        if (this.age > 30 || this.getEntityWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else {
            if (vec3d.length() < 5) this.setVelocity(vec3d.multiply(1.1F));
            this.setPosition(d, e, f);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof SpectreEntity) return;
        Entity entity = entityHitResult.getEntity();
        World world = entity.getEntityWorld();
        if (world instanceof ServerWorld serverWorld) entity.damage(serverWorld, this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0.5F + 0.5F * (world.getDifficulty().ordinal() - 1));
        this.discard();
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getEntityWorld().isClient()) {
            this.discard();
        }

    }
}

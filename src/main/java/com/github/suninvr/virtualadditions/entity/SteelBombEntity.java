package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SteelBombEntity extends ThrowableItemProjectile {
    ItemStack stack;
    int fuseLength;

    private final ExplosionDamageCalculator explosionBehavior = new EntityBasedExplosionDamageCalculator(this) {
        @Override
        public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float power) {
            ExplosiveContentComponent component = SteelBombEntity.this.stack.get(VADataComponentTypes.EXPLOSIVE_CONTENTS);
            if (component != null && !component.shouldDestroyBlocks()) return false;
            return super.shouldBlockExplode(explosion, world, pos, state, power);
        }

        @Override
        public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
            return !(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb);
        }

        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity, float amount) {
            return Math.min(super.getEntityDamageAmount(explosion, entity, amount) * 1.25F, 6.0F * explosion.radius());
        }
    };

    public SteelBombEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public SteelBombEntity(Level world, LivingEntity owner) {
        super(VAEntityType.STEEL_BOMB, owner, world, VAItems.STEEL_BOMB.getDefaultInstance());
    }

    public SteelBombEntity(Level world, double x, double y, double z) {
        super(VAEntityType.STEEL_BOMB, x, y, z, world, VAItems.STEEL_BOMB.getDefaultInstance());
    }

    public SteelBombEntity(ServerLevel serverWorld, LivingEntity livingEntity, ItemStack itemStack) {
        super(VAEntityType.STEEL_BOMB, livingEntity, serverWorld, itemStack);
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        this.explode();
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
    }

    protected void explode() {
        if (!this.level().isClientSide()) {
            this.level().explode(this, this.level().damageSources().explosion(this, this.getOwner()), this.explosionBehavior, this.getX(), this.getY(), this.getZ(), this.getExplosivePower(), this.isOnFire(), Level.ExplosionInteraction.TNT);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected float getExplosivePower() {
        ExplosiveContentComponent component = this.stack.get(VADataComponentTypes.EXPLOSIVE_CONTENTS);
        if (component != null) return component.getExplosionStrength();
        return 2;
    }

    protected int getFuseLength() {
        ExplosiveContentComponent component = this.stack.get(VADataComponentTypes.EXPLOSIVE_CONTENTS);
        if (component != null) return component.getFuseLength();
        return 200;
    }

    @Override
    protected Item getDefaultItem() {
        return VAItems.STEEL_BOMB;
    }

    @Override
    public void setItem(ItemStack stack) {
        super.setItem(stack);
        this.stack = stack;
        this.fuseLength = this.getFuseLength();
    }

    @Override
    public void tick() {
        super.tick();
        if( this.fuseLength > 0 ) {
            this.fuseLength -= 1;
        } else if (this.fuseLength == 0) {
            this.explode();
        }
        if(this.level().isClientSide() && !this.wasEyeInWater) {
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0F, 0.05F, 0.0F);
        }
    }
}

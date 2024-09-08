package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class SteelBombEntity extends ThrownItemEntity {
    ItemStack stack;
    int fuseLength;

    private final ExplosionBehavior explosionBehavior = new EntityExplosionBehavior(this) {
        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            ExplosiveContentComponent component = SteelBombEntity.this.stack.get(VADataComponentTypes.EXPLOSIVE_CONTENTS);
            if (component != null && !component.shouldDestroyBlocks()) return false;
            return super.canDestroyBlock(explosion, world, pos, state, power);
        }

        @Override
        public boolean shouldDamage(Explosion explosion, Entity entity) {
            return !(entity instanceof ItemEntity);
        }
    };

    public SteelBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SteelBombEntity(World world, LivingEntity owner) {
        super(VAEntityType.STEEL_BOMB, owner, world, VAItems.STEEL_BOMB.getDefaultStack());
    }

    public SteelBombEntity(World world, double x, double y, double z) {
        super(VAEntityType.STEEL_BOMB, x, y, z, world, VAItems.STEEL_BOMB.getDefaultStack());
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.explode();
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    protected void explode() {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), this.explosionBehavior, this.getX(), this.getY(), this.getZ(), this.getExplosivePower(), this.isOnFire(), World.ExplosionSourceType.TNT);
            this.getWorld().sendEntityStatus(this, (byte)3);
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
        if(this.getWorld().isClient() && !this.submergedInWater) {
            getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0F, 0.05F, 0.0F);
        }
    }
}

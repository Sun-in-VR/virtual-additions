package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SteelBombEntity extends ThrownItemEntity {
    ItemStack stack;
    int fuseLength;

    public SteelBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SteelBombEntity(World world, LivingEntity owner) {
        super(VAEntityType.STEEL_BOMB, owner, world);
    }

    public SteelBombEntity(World world, double x, double y, double z) {
        super(VAEntityType.STEEL_BOMB, x, y, z, world);
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
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), this.getExplosivePower(), this.isOnFire(), World.ExplosionSourceType.TNT);
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

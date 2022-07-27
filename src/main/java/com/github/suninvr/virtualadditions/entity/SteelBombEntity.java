package com.github.suninvr.virtualadditions.entity;

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
import net.minecraft.world.explosion.Explosion;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;

public class SteelBombEntity extends ThrownItemEntity {
    NbtCompound stackNbt;
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
        if (!this.world.isClient) {
            this.world.createExplosion(this, null, null, this.getX(), this.getY(), this.getZ(), this.getExplosivePower(), this.isOnFire(), Explosion.DestructionType.BREAK);
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    protected float getExplosivePower() {
        float power;
        if (this.stackNbt != null) {
            power = this.stackNbt.getFloat("Power");
            power = power <= 0 ? 2.0F : power;
        } else power = 2.0F;
        return power;
    }

    protected int getFuseLength() {
        int fuseLength;
        if (this.stackNbt != null) {
            fuseLength = this.stackNbt.getInt("FuseLength");
            fuseLength = fuseLength <= 0 ? -1 : fuseLength;
        } else fuseLength = -1;
        return fuseLength;
    }

    @Override
    protected Item getDefaultItem() {
        return VAItems.STEEL_BOMB;
    }

    @Override
    public void setItem(ItemStack item) {
        super.setItem(item);
        this.stackNbt = item.getNbt();
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
        if(this.world.isClient() && !this.submergedInWater) {
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0F, 0.05F, 0.0F);
        }
    }
}

package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SlungItemProjectile extends ThrowableItemProjectile {
    private float damage = 1.0F;

    public SlungItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SlungItemProjectile(double d, double e, double f, Level level, ItemStack itemStack) {
        super(VAEntityType.SLUNG_ITEM, d, e, f, level, itemStack);
    }

    public SlungItemProjectile(ServerLevel serverLevel, LivingEntity entity, ItemStack stack) {
        super(VAEntityType.SLUNG_ITEM, serverLevel);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.level().playSound(this, this.getX(), this.getY(), this.getZ(), SoundEvents.SPEAR_HIT.value(), this.getSoundSource(), 0.25F, 1.6F);
            this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
            ItemEntity entity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem());
            entity.setPickUpDelay(10);
            this.level().addFreshEntity(entity);
        }
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putFloat("damage", this.damage);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.damage = valueInput.getFloatOr("damage", 1.0F);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET;
    }
}

package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class SalineEntity extends ZombieEntity {
    public SalineEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer createSalineAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.19f)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.5)
                .add(EntityAttributes.ARMOR, 2.0)
                .add(EntityAttributes.SPAWN_REINFORCEMENTS)
                .build();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return VASoundEvents.ENTITY_SALINE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return VASoundEvents.ENTITY_SALINE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return VASoundEvents.ENTITY_SALINE_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_HUSK_STEP;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            Difficulty diff = this.getWorld().getDifficulty();
            float f = this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            int g = diff == Difficulty.HARD ? 2 : 1;
            int h = diff == Difficulty.HARD ? 4 : 2;
            int i = ((LivingEntity) target).hasStatusEffect(VAStatusEffects.FRAILTY) ? Math.min(((LivingEntity) target).getStatusEffect(VAStatusEffects.FRAILTY).getAmplifier() + g, h) : 0;
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(VAStatusEffects.FRAILTY, 300 * (int)f, i), this);
        }
        return bl;
    }

    @Override
    protected boolean canConvertInWater() {
        return true;
    }

    @Override
    protected void convertInWater() {
        this.convertTo(EntityType.ZOMBIE);
        if (!this.isSilent()) {
            this.getWorld().syncWorldEvent(null, WorldEvents.HUSK_CONVERTS_TO_ZOMBIE, this.getBlockPos(), 0);
        }
    }

    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

}

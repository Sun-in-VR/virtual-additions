package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;

public class SalineEntity extends Zombie {
    public SalineEntity(EntityType<? extends Zombie> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier createSalineAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.19f)
                .add(Attributes.ATTACK_DAMAGE, 3.5)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
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
        return SoundEvents.HUSK_STEP;
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        boolean bl = super.doHurtTarget(world, target);
        if (bl && this.getMainHandItem().isEmpty() && target instanceof LivingEntity) {
            Difficulty diff = this.level().getDifficulty();

            float f = world.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            int g = diff == Difficulty.HARD ? 2 : 1;
            int h = diff == Difficulty.HARD ? 4 : 2;
            int i = ((LivingEntity) target).hasEffect(VAStatusEffects.FRAILTY) ? Math.min(((LivingEntity) target).getEffect(VAStatusEffects.FRAILTY).getAmplifier() + g, h) : 0;
            ((LivingEntity)target).addEffect(new MobEffectInstance(VAStatusEffects.FRAILTY, 300 * (int)f, i), this);
        }
        return bl;
    }

    @Override
    protected boolean convertsInWater() {
        return true;
    }

    @Override
    protected void doUnderWaterConversion(ServerLevel serverWorld) {
        this.convertToZombieType(serverWorld, EntityType.ZOMBIE);
        if (!this.isSilent()) {
            this.level().levelEvent(null, LevelEvent.SOUND_HUSK_TO_ZOMBIE, this.blockPosition(), 0);
        }
    }

}

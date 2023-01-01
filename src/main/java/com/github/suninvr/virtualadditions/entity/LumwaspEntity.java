package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.registry.VADamageSource;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LumwaspEntity extends HostileEntity implements RangedAttackMob, Flutterer {
    public LumwaspEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer createLumwaspAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.55)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .build();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        int i = 0;
        this.playSound(SoundEvents.ENTITY_LLAMA_SPIT, 1.0F, 1.0F);
        while (i <= 2) {
            AcidSpitEntity projectile = new AcidSpitEntity(this.world, this);
            double d = target.getEyeY() - 1.100000023841858;
            double e = target.getX() - this.getX();
            double f = d - projectile.getY();
            double g = target.getZ() - this.getZ();
            double h = Math.sqrt(e * e + g * g) * 0.20000000298023224;
            projectile.setVelocity(e, f + h, g, 1.6F, 10.0F);
            this.world.spawnEntity(projectile);
            i++;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AvoidSunlightGoal(this));
        this.goalSelector.add(1, new EscapeSunlightGoal(this, 1.0D));
        this.goalSelector.add(2, new MeleeCloseRangeGoal(this, 1.0D, 4, true));
        this.goalSelector.add(3, new ProjectileAttackGoal(this, 1.0D, 45, 8));
        this.goalSelector.add(4, new FlyGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, LumwaspEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, BeeEntity.class, true));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return VASoundEvents.ENTITY_LUMWASP_HURT;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || damageSource == VADamageSource.ACID;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isInAir();
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    private class MeleeCloseRangeGoal extends MeleeAttackGoal {
        private final int startRange;
        private final int continueRange;
        public MeleeCloseRangeGoal(PathAwareEntity mob, double speed, int range, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.startRange = range * range;
            this.continueRange = (2 + range) * (2 + range);
        }

        @Override
        public boolean canStart() {
            return checkDistance(this.startRange) && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return checkDistance(this.continueRange) && super.shouldContinue();
        }

        private boolean checkDistance(int range) {
            boolean bl = false;
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                bl = (this.mob.squaredDistanceTo(target) <= range);
            }
            return bl;
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.0F + entity.getWidth();
        }
    }
}

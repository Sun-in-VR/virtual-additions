package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class LumwaspEntity extends Monster implements RangedAttackMob, FlyingAnimal {
    public LumwaspEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    public static AttributeSupplier createLumwaspAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.FLYING_SPEED, 0.135)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .build();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        int i = 0;
        this.playSound(SoundEvents.LLAMA_SPIT, 1.0F, 1.0F);
        double d = target.getEyeY() - 1.100000023841858;
        double e = target.getX() - this.getX();
        double g = target.getZ() - this.getZ();
        double h = Math.sqrt(e * e + g * g) * 0.20000000298023224;
        while (i <= 2) {
            AcidSpitEntity projectile = new AcidSpitEntity(this.level(), this);
            double f = d - projectile.getY();
            projectile.shoot(e, f + h, g, 1.6F, 10.0F);
            this.level().addFreshEntity(projectile);
            i++;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AlwaysEscapeSunlightGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new MeleeCloseRangeGoal(this, 1.2D, 4, true));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.1D, 45, 8));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, LumwaspEntity.class, Breeze.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Bee.class, true));
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world) {
            public boolean isStableDestination(BlockPos pos) {
                return this.level.canSeeSky(pos);
            }

            protected void trimPath() {
                super.trimPath();
                if (this.level.canSeeSky(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()))) {
                    return;
                }
                if (this.path != null) {
                    for (int i = 0; i < this.path.getNodeCount(); ++i) {
                        Node pathNode = this.path.getNode(i);
                        if (this.level.canSeeSky(new BlockPos(pathNode.x, pathNode.y, pathNode.z))) {
                            this.path.truncateNodes(i);
                            return;
                        }
                    }
                }
            }
        };
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanFloat(false);
        return birdNavigation;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return VASoundEvents.ENTITY_LUMWASP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return VASoundEvents.ENTITY_LUMWASP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel world, DamageSource source) {
        return source.is(VADamageTypes.ACID_TAG) || super.isInvulnerableTo(world, source);
    }

    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected boolean isFlapping() {
        return this.isFlying();
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return super.checkSpawnObstruction(world);
    }

    public static boolean canSpawnLumwasp(EntityType<? extends Monster> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && (EntitySpawnReason.ignoresLightRequirements(spawnReason) || LumwaspEntity.isDarkEnoughToSpawn(world, pos, random)) && Monster.checkMobSpawnRules(type, world, spawnReason, pos, random);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        if (world.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        }
        if (world.getBrightness(LightLayer.BLOCK, pos) > 7) {
            return false;
        }
        DimensionType dimensionType = world.dimensionType();
        int j = world.getLevel().isThundering() ? world.getMaxLocalRawBrightness(pos, 10) : world.getMaxLocalRawBrightness(pos);
        return j <= dimensionType.monsterSpawnLightTest().sample(random);
    }

    @Override
    protected void customServerAiStep(ServerLevel world) {
        if (this.isUnderWater() && !((EntityInterface)this).virtualAdditions$isInAcid() ) {
            this.hurtServer(world, this.damageSources().drown(), 1.0F);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        boolean bl = super.doHurtTarget(world, target);
        if (bl && world.getDifficulty().compareTo(Difficulty.EASY) > 0 && target instanceof LivingEntity livingEntity) {
            if (!this.getActiveEffects().isEmpty()) {
                for (MobEffectInstance statusEffect : this.getActiveEffects()) {
                    livingEntity.addEffect(statusEffect, this);
                }
            } else {
                int duration = 100;
                duration += (this.level().getDifficulty().getId() - 1) * 50;
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration), this);
            }

        }
        return bl;
    }


    @Override
    public void travel(Vec3 movementInput) {
        if (this.onGround()) {
            super.travel(movementInput);
            return;
        }
        this.travelFlying(movementInput, this.getSpeed());
    }

    private static class MeleeCloseRangeGoal extends MeleeAttackGoal {
        private final int startRange;
        private final int continueRange;
        public MeleeCloseRangeGoal(PathfinderMob mob, double speed, int range, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.startRange = range * range;
            this.continueRange = (2 + range) * (2 + range);
        }

        @Override
        public boolean canUse() {
            return checkDistance(this.startRange) && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return checkDistance(this.continueRange) && super.canContinueToUse();
        }

        private boolean checkDistance(int range) {
            boolean bl = false;
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                bl = (this.mob.distanceToSqr(target) <= range);
            }
            return bl;
        }
    }
    private static class AlwaysEscapeSunlightGoal extends FleeSunGoal {

        private final Level world;

        public AlwaysEscapeSunlightGoal(PathfinderMob mob, double speed) {
            super(mob, speed);
            this.world = mob.level();
        }

        @Override
        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            } else if (!this.world.isBrightOutside()) {
                return false;
            } else if (!this.world.canSeeSky(this.mob.blockPosition())) {
                return false;
            } else {
                return this.setWantedPos();
            }
        }
    }
}

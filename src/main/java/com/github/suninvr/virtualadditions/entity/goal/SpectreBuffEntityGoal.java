package com.github.suninvr.virtualadditions.entity.goal;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class SpectreBuffEntityGoal extends Goal {
    private final SpectreEntity mob;
    @Nullable
    private Mob target;
    private final double speed;
    private final PathNavigation navigation;
    private int updateCountdownTicks;
    private long targetLastSeen;
    private final float minDistance;
    private final float maxDistance;
    private final float buffMaxDistance;
    private List<Mob> nearbyMobs = new ArrayList<>();
    private static final Predicate<Mob> targetPredicate;
    private static final Map<Class<?>, Integer> targetPriority = new HashMap<>();

    public SpectreBuffEntityGoal(SpectreEntity mob, double speed, float buffMaxDistance, float minDistance, float maxDistance) {
        this.mob = mob;
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.buffMaxDistance = buffMaxDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(mob.getNavigation() instanceof GroundPathNavigation) && !(mob.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean canUse() {
        if (canTargetMob( (Mob) this.mob.getBuffTarget())) {
            this.target = (Mob) this.mob.getBuffTarget();
            return true;
        }

        this.updateList();
        Mob entity = null;
        int priority = -1;
        double distance = Double.MAX_VALUE;
        for (Mob mobEntity : this.nearbyMobs) {
            if (!canStartTargetMob(mobEntity)) continue;
            int i = getPriority(mobEntity);
            double d = this.mob.distanceTo(mobEntity);
            if (i > priority || (i == priority && d < distance)) {
                entity = mobEntity;
                priority = i;
                distance = d;
            }
        }
        if (entity != null) {
            this.setTarget(entity);
            return true;
        }
        return false;
    }

    private boolean canStartTargetMob(Mob entity) {
        if (entity == null) return false;
        UUID spectreId = SpectreEntity.getBuffingSpectreId(entity);
        boolean isAvailable = spectreId == null || spectreId.equals(this.mob.getUUID());
        return canTargetMob(entity) && isAvailable && (this.mob.getBuffTarget() == entity || this.mob.getSensing().hasLineOfSight(entity));
    }

    private boolean canTargetMob(Mob entity) {
        return entity != null && !entity.isDeadOrDying() && !entity.isRemoved() && !entity.isInvisible() && !(this.mob.distanceTo(entity) > 24);
    }

    private void updateList() {
        this.nearbyMobs = this.mob.level().getEntitiesOfClass(Mob.class, this.mob.getBoundingBox().inflate(this.maxDistance), this.targetPredicate);
    }

    @Override
    public boolean canContinueToUse() {
        return this.canStartTargetMob(this.target) && this.mob.level().getGameTime() - this.targetLastSeen < 100;
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
    }

    @Override
    public void stop() {
        this.setTarget(null);
        this.navigation.stop();
    }

    private void setTarget(Mob entity) {
        this.target = entity;
        this.mob.setBuffTarget(entity);
    }

    @Override
    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().setLookAt(this.target, 10.0F, this.mob.getMaxHeadXRot());
            if (--this.updateCountdownTicks <= 0) {
                boolean bl = this.mob.getSensing().hasLineOfSight(this.target);
                if (bl) this.targetLastSeen = this.mob.level().getGameTime();
                this.updateCountdownTicks = this.adjustedTickDelay(10);
                double targetY = this.target.getEyeY();
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - targetY;
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!bl || !(g <= this.minDistance * this.minDistance)) {
                    this.navigation.moveTo(this.target.getX(), targetY, this.target.getZ(), this.speed);
                } else {
                    this.navigation.stop();
                    LookControl lookControl = this.target.getLookControl();
                    if (g <= this.minDistance
                            || lookControl.getWantedX() == this.mob.getX() && lookControl.getWantedY() == this.mob.getY() && lookControl.getWantedZ() == this.mob.getZ()) {
                        double h = this.target.getX() - this.mob.getX();
                        double i = this.target.getZ() - this.mob.getZ();
                        this.navigation.moveTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
                    }
                }
                this.mob.setIsBuffing(g <= this.buffMaxDistance * this.buffMaxDistance && bl);
            }
        }
    }

    private static int getPriority(Mob entity) {
        int i = targetPriority.getOrDefault(entity.getClass(), 0) + entity.getArmorValue();
        i += (int) (entity.getMaxHealth() - entity.getHealth());
        return i;
    }

    static {
        targetPriority.put(WitherSkeleton.class, 5);
        targetPriority.put(AbstractSkeleton.class, 3);
        targetPriority.put(Zombie.class, 2);
        targetPriority.put(Monster.class, 1);
        targetPredicate = target -> target != null && !target.isDeadOrDying() && !target.isRemoved() && SpectreEntity.class != target.getClass() && target.getType().is(VAEntityTypeTags.SPECTRE_BUFF_TARGETS);
    }
}

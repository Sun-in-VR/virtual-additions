package com.github.suninvr.virtualadditions.entity.goal;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class SpectreBuffEntityGoal extends Goal {
    private final SpectreEntity mob;
    @Nullable
    private MobEntity target;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private long targetLastSeen;
    private final float minDistance;
    private final float maxDistance;
    private final float buffMaxDistance;
    private List<MobEntity> nearbyMobs = new ArrayList<>();
    private static final Predicate<MobEntity> targetPredicate;
    private static final Map<Class<?>, Integer> targetPriority = new HashMap<>();

    public SpectreBuffEntityGoal(SpectreEntity mob, double speed, float buffMaxDistance, float minDistance, float maxDistance) {
        this.mob = mob;
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.buffMaxDistance = buffMaxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean canStart() {
        if (canTargetMob( (MobEntity) this.mob.getBuffTarget())) {
            this.target = (MobEntity) this.mob.getBuffTarget();
            return true;
        }

        this.updateList();
        MobEntity entity = null;
        int priority = -1;
        double distance = Double.MAX_VALUE;
        for (MobEntity mobEntity : this.nearbyMobs) {
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

    private boolean canStartTargetMob(MobEntity entity) {
        if (entity == null) return false;
        UUID spectreId = SpectreEntity.getBuffingSpectreId(entity);
        boolean isAvailable = spectreId == null || spectreId.equals(this.mob.getUuid());
        return canTargetMob(entity) && isAvailable && (this.mob.getBuffTarget() == entity || this.mob.getVisibilityCache().canSee(entity));
    }

    private boolean canTargetMob(MobEntity entity) {
        return entity != null && !entity.isDead() && !entity.isRemoved() && !entity.isInvisible() && !(this.mob.distanceTo(entity) > 24);
    }

    private void updateList() {
        this.nearbyMobs = this.mob.getEntityWorld().getEntitiesByClass(MobEntity.class, this.mob.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
    }

    @Override
    public boolean shouldContinue() {
        return this.canStartTargetMob(this.target) && this.mob.getEntityWorld().getTime() - this.targetLastSeen < 100;
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

    private void setTarget(MobEntity entity) {
        this.target = entity;
        this.mob.setBuffTarget(entity);
    }

    @Override
    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().lookAt(this.target, 10.0F, this.mob.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                boolean bl = this.mob.getVisibilityCache().canSee(this.target);
                if (bl) this.targetLastSeen = this.mob.getEntityWorld().getTime();
                this.updateCountdownTicks = this.getTickCount(10);
                double targetY = this.target.getEyeY();
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - targetY;
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!bl || !(g <= this.minDistance * this.minDistance)) {
                    this.navigation.startMovingTo(this.target.getX(), targetY, this.target.getZ(), this.speed);
                } else {
                    this.navigation.stop();
                    LookControl lookControl = this.target.getLookControl();
                    if (g <= this.minDistance
                            || lookControl.getLookX() == this.mob.getX() && lookControl.getLookY() == this.mob.getY() && lookControl.getLookZ() == this.mob.getZ()) {
                        double h = this.target.getX() - this.mob.getX();
                        double i = this.target.getZ() - this.mob.getZ();
                        this.navigation.startMovingTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
                    }
                }
                this.mob.setIsBuffing(g <= this.buffMaxDistance * this.buffMaxDistance && bl);
            }
        }
    }

    private static int getPriority(MobEntity entity) {
        int i = targetPriority.getOrDefault(entity.getClass(), 0) + entity.getArmor();
        i += (int) (entity.getMaxHealth() - entity.getHealth());
        return i;
    }

    static {
        targetPriority.put(WitherSkeletonEntity.class, 5);
        targetPriority.put(AbstractSkeletonEntity.class, 3);
        targetPriority.put(ZombieEntity.class, 2);
        targetPriority.put(HostileEntity.class, 1);
        targetPredicate = target -> target != null && !target.isDead() && !target.isRemoved() && SpectreEntity.class != target.getClass() && target.getType().isIn(VAEntityTypeTags.SPECTRE_BUFF_TARGETS);
    }
}

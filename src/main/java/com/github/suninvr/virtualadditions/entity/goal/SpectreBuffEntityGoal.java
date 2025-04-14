package com.github.suninvr.virtualadditions.entity.goal;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.*;
import net.minecraft.registry.tag.EntityTypeTags;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SpectreBuffEntityGoal extends Goal {
    private final SpectreEntity mob;
    private final Predicate<MobEntity> targetPredicate;
    @Nullable
    private MobEntity target;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private final float maxDistance;
    private final float buffMaxDistance;
    private static final Map<Class<?>, Integer> mobPriority = new HashMap<>();

    public SpectreBuffEntityGoal(SpectreEntity mob, double speed, float buffMaxDistance, float minDistance, float maxDistance) {
        this.mob = mob;
        this.targetPredicate = target -> target != null && mob.getClass() != target.getClass();
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
        List<MobEntity> list = this.mob.getWorld().getEntitiesByClass(MobEntity.class, this.mob.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
        MobEntity entity = null;
        int priority = -1;
        if (!list.isEmpty()) {
            for (MobEntity mobEntity : list) {
                int i;
                if (!mobEntity.isInvisible() && mobEntity.getType().isIn(VAEntityTypeTags.SPECTRE_BUFF_TARGETS) && (this.mob.canSee(mobEntity) || this.target == mobEntity) && (i = getPriority(mobEntity)) > priority) {
                    entity = mobEntity;
                    priority = i;
                }
            }
        }
        if (entity != null) {
            this.target = entity;
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null && !this.target.isDead() && !this.target.isRemoved(); //&& !this.navigation.isIdle();
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
    }

    @Override
    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().lookAt(this.target, 10.0F, this.mob.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                boolean bl = this.mob.canSee(this.target);
                this.updateCountdownTicks = this.getTickCount(10);
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - this.target.getY();
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!(g <= this.minDistance * this.minDistance) || !bl) {
                    this.navigation.startMovingTo(this.target.getX(), this.target.getY() + 3, this.target.getZ(), this.speed);
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
                if (g <= this.buffMaxDistance * this.buffMaxDistance && bl) {
                    this.mob.setBuffTarget(this.target);
                } else this.mob.setBuffTarget(null);
            }
        }
    }

    private int getPriority(MobEntity entity) {
        int i = mobPriority.getOrDefault(entity, 0) + entity.getArmor();
        i += (int) (entity.getMaxHealth() - entity.getHealth());
        return i;
    }

    static {
        mobPriority.put(WitherSkeletonEntity.class, 5);
        mobPriority.put(AbstractSkeletonEntity.class, 3);
        mobPriority.put(ZombieEntity.class, 2);
        mobPriority.put(HostileEntity.class, 1);
    }
}

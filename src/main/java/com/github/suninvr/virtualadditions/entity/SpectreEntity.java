package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.goal.SpectreBuffEntityGoal;
import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpectreEntity extends HostileEntity {
    private static final TrackedData<Boolean> IS_BUFFING_TARGET = DataTracker.registerData(SpectreEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<UUID> BUFF_TARGET = DataTracker.registerData(SpectreEntity.class, VATrackedDataHandlerRegistry.UUID);
    private static final UUID EMPTY_ID = UUID.fromString("0-0-0-0-0");
    private static final Map<UUID, UUID> spectreToTarget = new HashMap<>();
    private static final Map<UUID, UUID> targetToSpectre = new HashMap<>();
    private LivingEntity buffTarget = null;
    private UUID buffTargetId = EMPTY_ID;
    private int buffTicks = 0;

    public SpectreEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 210, true);
    }

    public static DefaultAttributeContainer createSpectreAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.FLYING_SPEED, 0.1)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.FOLLOW_RANGE, 16.0)
                .build();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_BUFFING_TARGET, false);
        builder.add(BUFF_TARGET, EMPTY_ID);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new SpectreBuffEntityGoal(this, 1.5, 8.0F, 5.0F, 16.0F));
        this.goalSelector.add(4, new FlyGoal(this, 1.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void travel(Vec3d movementInput) {
        this.travelFlying(movementInput, this.getMovementSpeed());
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.isDead()) return;
        if (!this.getWorld().isClient()) {
            if (this.checkBuffTarget() && this.isBuffingTarget()) {
                int difficulty = this.getWorld().getDifficulty().getId();
                if (this.age % 20 == 0) this.applyEffects(this.buffTarget, difficulty);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.getWorld().isClient()) {
            if (this.age % 40 == 0) this.refreshTarget();
            this.spawnAmbientEffects();
            if (this.isBuffingTarget()) {
                if (this.buffTarget instanceof LivingEntity livingEntity) {
                    this.spawnBuffEffects(livingEntity);
                }
            }
        }
        if (this.isBuffingTarget()) this.buffTicks += 1;
        else this.buffTicks = 0;
    }

    @Override
    public void onDataTrackerUpdate(List<DataTracker.SerializedEntry<?>> entries) {
        super.onDataTrackerUpdate(entries);
        if (this.getWorld().isClient()) this.refreshTarget();
    }

    @Environment(EnvType.CLIENT)
    private void refreshTarget() {
        this.buffTarget = (LivingEntity) this.getWorld().getEntity(this.dataTracker.get(BUFF_TARGET));
    }

    private void spawnAmbientEffects() {
        if (this.age % 3 == 0) {
            this.getWorld().addParticleClient(VAParticleTypes.SPECTRAL_FLAME, this.getParticleX(0.25), this.getBodyY(1), this.getParticleZ(0.25), 0.0, 0.0, 0.0);
        }
    }

    private void spawnBuffEffects(LivingEntity buffTarget) {
        if (buffTarget == null || this.isDead() || this.isRemoved()) return;
        for (int i = 0; i < 1; ++i) {
            Vec3d pos = new Vec3d(buffTarget.getParticleX(0.6), buffTarget.getRandomBodyY(), buffTarget.getParticleZ(0.6));
            ParticleEffect effect = new TrailParticleEffect(pos, 0xE0EFFF, this.getWorld().random.nextInt(20) + 10);
            this.getWorld().addParticleClient(effect, true, true, this.getParticleX(0.35), this.getBodyY(0.5), this.getParticleZ(0.35), 0.0, 0.0, 0.0);
        }
        if (this.buffTicks % 2 == 0) this.getWorld().addParticleClient(VAParticleTypes.SPECTRAL_POWER, buffTarget.getParticleX(1), buffTarget.getBodyY(0.25), buffTarget.getParticleZ(1), 0.0, 0.0, 0.0);
        if (this.buffTicks % 50 == 0 && !this.isSilent()) this.getWorld().playSoundClient(this.getX(), this.getY(), this.getZ(), VASoundEvents.ENTITY_SPECTRE_EMPOWER_AMBIENT, SoundCategory.HOSTILE, 0.2F, 1.0F, true);
    }

    private void applyEffects(LivingEntity buffTarget, int difficulty) {
        if (buffTarget == null) return;
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 30, 0, true, true));
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30, 0, true, true));
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30, difficulty - 1, true, true));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30, difficulty - 1, true, false));
    }

    public void setIsBuffing(boolean bl) {
        if (bl && this.isBuffingTarget()) return;
        this.dataTracker.set(IS_BUFFING_TARGET, bl);
        if (bl && !this.isSilent() && checkBuffTarget()) this.getWorld().playSound(this, this.getX(), this.getY(), this.getZ(), VASoundEvents.ENTITY_SPECTRE_EMPOWER_START, SoundCategory.HOSTILE, 0.6F, 1.0F);
    }

    public boolean isBuffingTarget() {
        return this.dataTracker.get(IS_BUFFING_TARGET);
    }

    public void setBuffTarget(MobEntity target) {
        targetToSpectre.remove(spectreToTarget.get(this.uuid));
        spectreToTarget.remove(this.uuid);
        if (target == null) {
            this.buffTarget = null;
            this.buffTargetId = EMPTY_ID;
            this.setIsBuffing(false);
        } else if (!(targetToSpectre.containsKey(target.getUuid()))) {
            this.buffTarget = target;
            UUID targetId = this.buffTarget.getUuid();
            UUID thisId = this.getUuid();
            targetToSpectre.put(targetId, thisId);
            spectreToTarget.put(thisId, targetId);
            this.buffTargetId = targetId;
        }
        this.dataTracker.set(BUFF_TARGET, this.buffTargetId);
        if (VirtualAdditions.DEBUG) VirtualAdditions.LOGGER.log(Level.INFO, "Spectre Target Maps: T->S = " + targetToSpectre.size() + ", S->T = " + spectreToTarget.size());
    }

    public LivingEntity getBuffTarget() {
        this.checkBuffTarget();
        return this.buffTarget;
    }

    public static UUID getBuffingSpectreId(LivingEntity entity) {
        return targetToSpectre.get(entity.getUuid());
    }

    private boolean checkBuffTarget() {
        if (this.buffTarget == null && buffTargetId != EMPTY_ID) this.setBuffTarget((MobEntity) this.getWorld().getEntity(buffTargetId));
        if (this.buffTarget == null) return false;
        if (this.buffTarget.isDead() || this.buffTarget.isRemoved()) this.buffTarget = null;
        if (buffTarget == null) this.setIsBuffing(false);
        return this.buffTarget != null;
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        this.buffTargetId = view.read("buff_target", Uuids.CODEC).orElse(EMPTY_ID);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        view.put("buff_target", Uuids.CODEC, this.buffTargetId);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return VASoundEvents.ENTITY_SPECTRE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return VASoundEvents.ENTITY_SPECTRE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return VASoundEvents.ENTITY_SPECTRE_DEATH;
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    public static boolean canSpawnInDark(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState state = world.getBlockState(pos.down());
        return (state.isIn(VABlockTags.SPECTRE_SPAWNABLE_ON) || !spawnReason.equals(SpawnReason.NATURAL)) && ((spawnReason.equals(SpawnReason.SPAWNER) && world.getBlockState(pos).isOf(VABlocks.SPECTRAL_FIRE)) || HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            @Override
            protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
                return super.canPathDirectlyThrough(origin, target);
            }
        };
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setMaxFollowRange(48.0F);
        return birdNavigation;
    }
}

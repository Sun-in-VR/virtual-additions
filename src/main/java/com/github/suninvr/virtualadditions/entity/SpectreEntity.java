package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.goal.SpectreBuffEntityGoal;
import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpectreEntity extends Monster {
    private static final EntityDataAccessor<Boolean> IS_BUFFING_TARGET = SynchedEntityData.defineId(SpectreEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<UUID> BUFF_TARGET = SynchedEntityData.defineId(SpectreEntity.class, VATrackedDataHandlerRegistry.UUID);
    private static final UUID EMPTY_ID = UUID.fromString("0-0-0-0-0");
    private static final Map<UUID, UUID> spectreToTarget = new HashMap<>();
    private static final Map<UUID, UUID> targetToSpectre = new HashMap<>();
    private LivingEntity buffTarget = null;
    private UUID buffTargetId = EMPTY_ID;
    private int buffTicks = 0;

    public SpectreEntity(EntityType<? extends Monster> entityType, net.minecraft.world.level.Level world) {
        super(entityType, world);
        this.moveControl = new FlyingMoveControl(this, 210, true);
    }

    public static AttributeSupplier createSpectreAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.FLYING_SPEED, 0.1)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .build();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BUFFING_TARGET, false);
        builder.define(BUFF_TARGET, EMPTY_ID);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SpectreBuffEntityGoal(this, 1.5, 8.0F, 5.0F, 16.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void travel(Vec3 movementInput) {
        this.travelFlying(movementInput, this.getSpeed());
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.isDeadOrDying()) return;
        if (!this.level().isClientSide()) {
            if (this.checkBuffTarget() && this.isBuffingTarget()) {
                int difficulty = this.level().getDifficulty().getId();
                if (this.tickCount % 20 == 0) this.applyEffects(this.buffTarget, difficulty);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            if (this.tickCount % 40 == 0) this.refreshTarget();
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
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> entries) {
        super.onSyncedDataUpdated(entries);
        if (this.level().isClientSide()) this.refreshTarget();
    }

    @Environment(EnvType.CLIENT)
    private void refreshTarget() {
        this.buffTarget = (LivingEntity) this.level().getEntity(this.entityData.get(BUFF_TARGET));
    }

    private void spawnAmbientEffects() {
        if (this.tickCount % 3 == 0) {
            this.level().addParticle(VAParticleTypes.SPECTRAL_FLAME, this.getRandomX(0.25), this.getY(1), this.getRandomZ(0.25), 0.0, 0.0, 0.0);
        }
    }

    private void spawnBuffEffects(LivingEntity buffTarget) {
        if (buffTarget == null || this.isDeadOrDying() || this.isRemoved()) return;
        for (int i = 0; i < 1; ++i) {
            Vec3 pos = new Vec3(buffTarget.getRandomX(0.6), buffTarget.getRandomY(), buffTarget.getRandomZ(0.6));
            ParticleOptions effect = new TrailParticleOption(pos, 0xE0EFFF, this.level().random.nextInt(20) + 10);
            this.level().addParticle(effect, true, true, this.getRandomX(0.35), this.getY(0.5), this.getRandomZ(0.35), 0.0, 0.0, 0.0);
        }
        if (this.buffTicks % 2 == 0) this.level().addParticle(VAParticleTypes.SPECTRAL_POWER, buffTarget.getRandomX(1), buffTarget.getY(0.25), buffTarget.getRandomZ(1), 0.0, 0.0, 0.0);
        if (this.buffTicks % 50 == 0 && !this.isSilent()) this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), VASoundEvents.ENTITY_SPECTRE_EMPOWER_AMBIENT, SoundSource.HOSTILE, 0.2F, 1.0F, true);
    }

    private void applyEffects(LivingEntity buffTarget, int difficulty) {
        if (buffTarget == null) return;
        buffTarget.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 30, 0, true, true));
        buffTarget.addEffect(new MobEffectInstance(MobEffects.SPEED, 30, 0, true, true));
        buffTarget.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 30, difficulty - 1, true, true));
        this.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 30, difficulty - 1, true, false));
    }

    public void setIsBuffing(boolean bl) {
        if (bl && this.isBuffingTarget()) return;
        this.entityData.set(IS_BUFFING_TARGET, bl);
        if (bl && !this.isSilent() && checkBuffTarget()) this.level().playSound(this, this.getX(), this.getY(), this.getZ(), VASoundEvents.ENTITY_SPECTRE_EMPOWER_START, SoundSource.HOSTILE, 0.6F, 1.0F);
    }

    public boolean isBuffingTarget() {
        return this.entityData.get(IS_BUFFING_TARGET);
    }

    public void setBuffTarget(Mob target) {
        targetToSpectre.remove(spectreToTarget.get(this.uuid));
        spectreToTarget.remove(this.uuid);
        if (target == null) {
            this.buffTarget = null;
            this.buffTargetId = EMPTY_ID;
            this.setIsBuffing(false);
        } else if (!(targetToSpectre.containsKey(target.getUUID()))) {
            this.buffTarget = target;
            UUID targetId = this.buffTarget.getUUID();
            UUID thisId = this.getUUID();
            targetToSpectre.put(targetId, thisId);
            spectreToTarget.put(thisId, targetId);
            this.buffTargetId = targetId;
        }
        this.entityData.set(BUFF_TARGET, this.buffTargetId);
        if (VirtualAdditions.DEBUG) VirtualAdditions.LOGGER.log(Level.INFO, "Spectre Target Maps: T->S = " + targetToSpectre.size() + ", S->T = " + spectreToTarget.size());
    }

    public LivingEntity getBuffTarget() {
        this.checkBuffTarget();
        return this.buffTarget;
    }

    public static UUID getBuffingSpectreId(LivingEntity entity) {
        return targetToSpectre.get(entity.getUUID());
    }

    private boolean checkBuffTarget() {
        if (this.buffTarget == null && buffTargetId != EMPTY_ID) this.setBuffTarget((Mob) this.level().getEntity(buffTargetId));
        if (this.buffTarget == null) return false;
        if (this.buffTarget.isDeadOrDying() || this.buffTarget.isRemoved()) this.buffTarget = null;
        if (buffTarget == null) this.setIsBuffing(false);
        return this.buffTarget != null;
    }

    @Override
    public void load(ValueInput view) {
        super.load(view);
        this.buffTargetId = view.read("buff_target", UUIDUtil.AUTHLIB_CODEC).orElse(EMPTY_ID);
    }

    @Override
    public void saveWithoutId(ValueOutput view) {
        super.saveWithoutId(view);
        view.store("buff_target", UUIDUtil.AUTHLIB_CODEC, this.buffTargetId);
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
    public boolean isSensitiveToWater() {
        return true;
    }

    public static boolean canSpawnSpectre(EntityType<? extends Monster> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        BlockState state = world.getBlockState(pos.below());
        return (state.is(VABlockTags.SPECTRE_SPAWNABLE_ON) || !spawnReason.equals(EntitySpawnReason.NATURAL)) && ((spawnReason.equals(EntitySpawnReason.SPAWNER) && world.getBlockState(pos).is(VABlocks.SPECTRAL_FIRE)) || Monster.checkMonsterSpawnRules(type, world, spawnReason, pos, random));
    }

    @Override
    protected PathNavigation createNavigation(net.minecraft.world.level.Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world) {
            @Override
            protected boolean canMoveDirectly(Vec3 origin, Vec3 target) {
                return super.canMoveDirectly(origin, target);
            }
        };
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanFloat(true);
        birdNavigation.setRequiredPathLength(48.0F);
        return birdNavigation;
    }
}

package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.entity.goal.SpectreBuffEntityGoal;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class SpectreEntity extends HostileEntity implements RangedAttackMob {
    private LivingEntity buffTarget = null;

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
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.0, 1.2));
        this.goalSelector.add(2, new SpectreBuffEntityGoal(this, 1.5, 8.0F, 5.0F, 16.0F));
        this.goalSelector.add(3, new FlyGoal(this, 1));
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void travel(Vec3d movementInput) {
        this.travelFlying(movementInput, this.getMovementSpeed());
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {

    }

    @Override
    public void tick() {
        this.noClip = true;
        super.tick();
        this.noClip = false;
        this.setNoGravity(true);
        if (this.isDead()) return;
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.spawnAmbientEffects(serverWorld);
            if (this.checkBuffTarget()) {
                this.spawnBuffEffects(this.buffTarget, serverWorld);
                int difficulty = this.getWorld().getDifficulty().getId();
                if (this.age % 40 == 0) this.applyEffects(this.buffTarget, difficulty);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    private void spawnAmbientEffects(ServerWorld serverWorld) {
        if (this.age % 4 == 0) serverWorld.spawnParticles(VAParticleTypes.SPECTRAL_FLAME, this.getX(), this.getY() + 0.575, this.getZ(), 1, 0.125, 0.125, 0.125, 0.0);
    }

    private void spawnBuffEffects(LivingEntity buffTarget, ServerWorld serverWorld) {
        if (buffTarget == null) return;

        Vec3d pos = buffTarget.getPos();
        double y = (Math.random() * buffTarget.getHeight());
        ParticleEffect effect = new TrailParticleEffect(pos.add(0, y, 0), 0xCFEEFF, 15);

        serverWorld.spawnParticles(effect, this.getX(), this.getY() + 0.375, this.getZ(), 1, 0.125, 0.125, 0.125, 0.0);
        if (this.age % 8 == 0) serverWorld.spawnParticles(VAParticleTypes.SPECTRAL_FLAME, buffTarget.getX(), buffTarget.getY() + buffTarget.getHeight() / 2, buffTarget.getZ(), 5, buffTarget.getWidth() * 0.45, buffTarget.getHeight() * 0.35, buffTarget.getWidth() * 0.45, 0.0);
        if (this.age % 60 == 0) serverWorld.playSound(this, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.HOSTILE, 1.0F, 1.3333F);

    }

    private void applyEffects(LivingEntity buffTarget, int difficulty) {
        if (buffTarget == null) return;
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0, true, false));
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 0, true, false));
        buffTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 2, true, false));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, difficulty - 1, true, false));
    }

    public void setBuffTarget(MobEntity target) {
        if (target == this.buffTarget) return;
        this.buffTarget = target;
        if (this.checkBuffTarget()) this.getWorld().playSound(this, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.HOSTILE, 1.0F, 1.3333F);
    }

    private boolean checkBuffTarget() {
        if (this.buffTarget == null) return false;
        if (this.buffTarget.isDead() || this.buffTarget.isRemoved()) this.buffTarget = null;
        return this.buffTarget != null;
    }

    public static boolean canSpawnInDark(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState state = world.getBlockState(pos.down());
        return (state.isOf(Blocks.GRASS_BLOCK) || state.isIn(BlockTags.NYLIUM)) && HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setMaxFollowRange(48.0F);
        return birdNavigation;
    }
}

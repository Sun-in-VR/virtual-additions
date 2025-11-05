package com.github.suninvr.virtualadditions.entity;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.network.PlayerProjectionMovementC2SPayload;
import com.github.suninvr.virtualadditions.network.PlayerProjectionS2CPayload;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VATrackedDataHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerProjectionEntity extends Avatar {
    private static final EntityDataAccessor<UUID> PLAYER_ID = SynchedEntityData.defineId(PlayerProjectionEntity.class, VATrackedDataHandlerRegistry.UUID);
    private static final UUID EMPTY_ID = UUID.fromString("0-0-0-0-0");
    private Player player;
    private boolean isPhasingThroughWall;
    public boolean lookDirectionChanged = false;
    private long isPhasingThroughWallLastCheck = -1;
    boolean isMainPlayer;

    public PlayerProjectionEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.FLYING_SPEED, 0.3)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .build();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PLAYER_ID, EMPTY_ID);
    }

    public static PlayerProjectionEntity createForPlayer(Player player) {
        PlayerProjectionEntity entity = VAEntityType.PLAYER_PROJECTION.create(player.level(), EntitySpawnReason.MOB_SUMMONED);
        if (entity == null) return null;
        Vec3 vec3d = player.pick(1.6F, 0, false).getLocation().add(0, entity.getEyeHeight() - entity.getBbHeight(), 0);
        entity.setPosRaw(vec3d.x, vec3d.y, vec3d.z);
        entity.setRot(player.getYRot(), player.getXRot());
        entity.yRotO = entity.yBodyRot = entity.yHeadRot = entity.getYRot();
        entity.player = player;
        entity.entityData.set(PLAYER_ID, player.getUUID());
        player.level().addFreshEntity(entity);
        if (player instanceof ServerPlayer serverPlayerEntity) ServerPlayNetworking.send(serverPlayerEntity, new PlayerProjectionS2CPayload(entity.uuid));
        entity.setCustomName(player.getName());
        ((PlayerEntityInterface)(player)).virtualAdditions$setProjectionEntity(entity);
        return entity;
    }

    @Override
    public boolean isInvulnerable() {
        if (this.player != null) return super.isInvulnerable() || this.player.getAbilities().invulnerable;
        return super.isInvulnerable();
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (this.isInvulnerable() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;
        return super.hurtServer(world, source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPlayer() == null || this.getPlayer().isRemoved() || !this.getPlayer().isUsingItem() || !this.getPlayer().getUseItem().is(VAItems.SPECTRAL_SPYGLASS) || this.distanceTo(this.getPlayer()) > 120) {
            if (!this.level().isClientSide()) {
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            this.isMainPlayer = this.getPlayer().isLocalPlayer();
        }
    }

    @Override
    protected void tickHeadTurn(float bodyRotation) {
        super.tickHeadTurn(bodyRotation);
    }

    @Override
    public void lerpHeadTo(float yaw, int interpolationSteps) {
        super.lerpHeadTo(yaw, interpolationSteps);
    }

    @Override
    protected void lerpPositionAndRotationStep(int step, double x, double y, double z, double yaw, double pitch) {
        super.lerpPositionAndRotationStep(step, x, y, z, yaw, pitch);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.player == null && this.entityData.get(PLAYER_ID) instanceof UUID playerId && !playerId.equals(EMPTY_ID)) {
            this.player = this.level().getPlayerByUUID(playerId);
        }
        if (this.level().isClientSide() && this.player != null && this.tickCount % 2 == 0) {
            this.spawnTrailParticles();
        }
    }

    public boolean isPhasingThroughWall() {
        if (this.isPhasingThroughWallLastCheck != this.level().getGameTime()) {
            this.noPhysics = false;
            this.isPhasingThroughWall = this.isInWall();
            this.noPhysics = true;
            this.isPhasingThroughWallLastCheck = this.level().getGameTime();
        }
        return this.isPhasingThroughWall;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientRemoval() {
        if (this.getPlayer() != null) {
            if (this.getPlayer().isLocalPlayer()) Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.getPlayer() instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.getCooldowns().isOnCooldown(VAItems.SPECTRAL_SPYGLASS.getDefaultInstance())) serverPlayer.getCooldowns().addCooldown(VAItems.SPECTRAL_SPYGLASS.getDefaultInstance(), 20);
            serverPlayer.stopUsingItem();
            ((PlayerEntityInterface)(player)).virtualAdditions$setProjectionEntity(null);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected void tickRidden(Player controllingPlayer, Vec3 movementInput) {
        super.tickRidden(controllingPlayer, movementInput);
        if (controllingPlayer instanceof LocalPlayer clientPlayerEntity) {
            Vec2 vec2f1 = LocalPlayer.modifyInputSpeedForSquareMovement(clientPlayerEntity.input.getMoveVector());
            double vertical = clientPlayerEntity.input.keyPresses.jump() ? 0.16 : clientPlayerEntity.input.keyPresses.shift() ? -0.16 : 0.0;
            Vec3 movement = new Vec3(vec2f1.x * 0.2, vertical , vec2f1.y * 0.2);
            float speed = (float) this.getAttributeValue(Attributes.FLYING_SPEED);
            double distance = this.distanceTo(controllingPlayer);
            if (distance > 64.0) {
                Vec3 toPlayer = new Vec3(controllingPlayer.getX() - this.getX(), controllingPlayer.getEyeY() - this.getEyeY(), controllingPlayer.getZ() - this.getZ());
                toPlayer = toPlayer.normalize().scale((distance - 64) * 0.05);
                this.push(toPlayer);
            }
            this.moveRelative(speed, movement);
            this.move(MoverType.SELF, this.getDeltaMovement());
            float g = this.isPhasingThroughWall() ? 0.0F : 0.8F;
            this.setDeltaMovement(this.getDeltaMovement().scale(g));
        }
    }

    @Environment(EnvType.CLIENT)
    private void spawnTrailParticles() {
        if (this.getPlayer() == null) return;
        Vec3 playerRelative = this.player.getEyePosition().subtract(this.getEyePosition());
        Vec3 pos = new Vec3(this.getRandomX(0.35), this.getRandomY(), this.getRandomZ(0.35)).add(playerRelative.scale(0.5 / Math.max(playerRelative.length(), 0.001)));
        Vec3 playerPos = new Vec3(this.player.getRandomX(0.6), this.player.getRandomY(), this.player.getRandomZ(0.6));
        ParticleOptions effect = new TrailParticleOption(playerPos, 0xE0EFFF, this.level().random.nextInt(20) + 10);
        this.level().addParticle(effect, true, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
    }

    @Environment(EnvType.CLIENT)
    public void sendMovementPackets() {
        boolean anglesChanged = this.lookDirectionChanged;
        boolean posChanged = this.position().x != this.xo || this.position().y != this.yo || this.position().z != this.zo;
        PlayerProjectionMovementC2SPayload payload = null;
        if (anglesChanged && posChanged) {
            payload = PlayerProjectionMovementC2SPayload.createFull(this);
        } else if (posChanged) {
            payload = PlayerProjectionMovementC2SPayload.createPosOnly(this);
        } else if (anglesChanged) {
            payload = PlayerProjectionMovementC2SPayload.createAnglesOnly(this);
        }
        this.lookDirectionChanged = false;
        if (payload != null) ClientPlayNetworking.send(payload);
    }

    @Override
    public void die(DamageSource damageSource) {
        if (this.getPlayer() != null && this.level() instanceof ServerLevel serverWorld) {
            this.player.hurtServer(serverWorld, ((DamageSourcesInterface)this.damageSources()).virtualAdditions$soulDestroyed(this, damageSource.getEntity()), 1000);
        }
    }

    @Override
    public float getViewYRot(float tickProgress) {
        return this.isLocalClientAuthoritative() ? this.yHeadRot : super.getYRot();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getPlayer();
    }

    @Override
    protected boolean isLocalClientAuthoritative() {
        return this.isMainPlayer;
    }

    @Override
    public boolean isClientAuthoritative() {
        return true;
    }

    @Override
    public HumanoidArm getMainArm() {
        return this.getPlayer() != null ? this.getPlayer().getMainArm() : super.getMainArm();
    }

    @Override
    public boolean isModelPartShown(PlayerModelPart part) {
        return (part.getId().equals("head") || part.getId().equals("hat")) && super.isModelPartShown(part);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.getType().getDimensions().scale(this.getAgeScale());
    }

    public Player getPlayer() {
        return this.player;
    }

    @Environment(EnvType.CLIENT)
    public void setClientPlayer(LocalPlayer player) {
        this.player = player;
    }
}

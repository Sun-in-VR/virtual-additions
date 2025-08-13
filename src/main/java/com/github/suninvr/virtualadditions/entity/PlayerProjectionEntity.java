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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerProjectionEntity extends LivingEntity {
    private static final TrackedData<UUID> PLAYER_ID = DataTracker.registerData(PlayerProjectionEntity.class, VATrackedDataHandlerRegistry.UUID);
    private static final UUID EMPTY_ID = UUID.fromString("0-0-0-0-0");
    private PlayerEntity player;
    private boolean isPhasingThroughWall;
    public boolean lookDirectionChanged = false;
    private long isPhasingThroughWallLastCheck = -1;

    public PlayerProjectionEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(PLAYER_ID, EMPTY_ID);
    }

    public static PlayerProjectionEntity createForPlayer(PlayerEntity player) {
        PlayerProjectionEntity entity = VAEntityType.PLAYER_PROJECTION.create(player.getEntityWorld(), SpawnReason.MOB_SUMMONED);
        if (entity == null) return null;
        Vec3d vec3d = player.getEyePos().add(player.getRotationVector().multiply(1.6));
        entity.setPos(vec3d.x, vec3d.y, vec3d.z);
        entity.setRotation(player.getYaw(), player.getPitch());
        entity.lastYaw = entity.bodyYaw = entity.headYaw = entity.getYaw();
        entity.player = player;
        entity.dataTracker.set(PLAYER_ID, player.getUuid());
        player.getEntityWorld().spawnEntity(entity);
        if (player instanceof ServerPlayerEntity serverPlayerEntity) ServerPlayNetworking.send(serverPlayerEntity, new PlayerProjectionS2CPayload(entity.uuid));
        entity.setCustomName(player.getName());
        ((PlayerEntityInterface)(player)).virtualAdditions$setProjectionEntity(entity);
        return entity;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPlayer() == null || this.getPlayer().isRemoved() || !this.getPlayer().isUsingItem() || !this.getPlayer().getActiveItem().isOf(VAItems.SPECTRAL_SPYGLASS) || this.distanceTo(this.getPlayer()) > 72) {
            if (!this.getEntityWorld().isClient()) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void turnHead(float bodyRotation) {
        if ((this.getPlayer() != null && this.getPlayer().isMainPlayer())) return;
        super.turnHead(bodyRotation);
    }

    @Override
    public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
        if ((this.getPlayer() != null && this.getPlayer().isMainPlayer())) return;
        super.updateTrackedHeadRotation(yaw, interpolationSteps);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.player == null && this.dataTracker.get(PLAYER_ID) instanceof UUID playerId && !playerId.equals(EMPTY_ID)) {
            this.player = this.getEntityWorld().getPlayerByUuid(playerId);
        }
        if (this.getEntityWorld().isClient() && this.player != null && this.age % 2 == 0) {
            this.spawnTrailParticles();
        }
    }

    public boolean isPhasingThroughWall() {
        if (this.isPhasingThroughWallLastCheck != this.getEntityWorld().getTime()) {
            this.noClip = false;
            this.isPhasingThroughWall = this.isInsideWall();
            this.noClip = true;
            this.isPhasingThroughWallLastCheck = this.getEntityWorld().getTime();
        }
        return this.isPhasingThroughWall;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onRemoved() {
        if (this.getPlayer() != null) {
            if (this.getPlayer().isMainPlayer()) MinecraftClient.getInstance().setCameraEntity(MinecraftClient.getInstance().player);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.getPlayer() instanceof ServerPlayerEntity serverPlayer) {
            if (!serverPlayer.getItemCooldownManager().isCoolingDown(VAItems.SPECTRAL_SPYGLASS.getDefaultStack())) serverPlayer.getItemCooldownManager().set(VAItems.SPECTRAL_SPYGLASS.getDefaultStack(), 20);
            serverPlayer.clearActiveItem();
            ((PlayerEntityInterface)(player)).virtualAdditions$setProjectionEntity(null);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        if (controllingPlayer instanceof ClientPlayerEntity clientPlayerEntity) {
            Vec2f vec2f1 = ClientPlayerEntity.applyDirectionalMovementSpeedFactors(clientPlayerEntity.input.getMovementInput());
            double d = clientPlayerEntity.input.playerInput.jump() ? 0.2 : clientPlayerEntity.input.playerInput.sneak() ? -0.2 : 0.0;
            Vec3d vec3d = new Vec3d(vec2f1.x * 0.2, d , vec2f1.y * 0.2);
            this.updateVelocity(0.15F, vec3d);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(this.isPhasingThroughWall() ? 0.2F : 0.91F));
        }
    }

    @Environment(EnvType.CLIENT)
    private void spawnTrailParticles() {
        if (this.getPlayer() == null) return;
        Vec3d playerRelative = this.player.getEyePos().subtract(this.getEyePos());
        Vec3d pos = new Vec3d(this.getParticleX(0.35), this.getRandomBodyY(), this.getParticleZ(0.35)).add(playerRelative.multiply(0.5 / Math.max(playerRelative.length(), 0.001)));
        Vec3d playerPos = new Vec3d(this.player.getParticleX(0.6), this.player.getRandomBodyY(), this.player.getParticleZ(0.6));
        ParticleEffect effect = new TrailParticleEffect(playerPos, 0xE0EFFF, this.getEntityWorld().random.nextInt(20) + 10);
        this.getEntityWorld().addParticleClient(effect, true, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
    }

    @Environment(EnvType.CLIENT)
    public void sendMovementPackets() {
        boolean anglesChanged = this.lookDirectionChanged;
        boolean posChanged = this.getPos().x != this.lastX || this.getPos().y != this.lastY || this.getPos().z != this.lastZ;
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
    public void onDeath(DamageSource damageSource) {
        if (this.getPlayer() != null && this.getEntityWorld() instanceof ServerWorld serverWorld) {
            this.player.damage(serverWorld, ((DamageSourcesInterface)this.getDamageSources()).virtualAdditions$soulDestroyed(this, damageSource.getAttacker()), 1000);
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getPlayer();
    }

    @Override
    protected boolean isControlledByMainPlayer() {
        return this.player != null && this.player.isMainPlayer();
    }

    @Override
    public boolean isControlledByPlayer() {
        return true;
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    @Environment(EnvType.CLIENT)
    public void setClientPlayer(ClientPlayerEntity player) {
        this.player = player;
    }
}

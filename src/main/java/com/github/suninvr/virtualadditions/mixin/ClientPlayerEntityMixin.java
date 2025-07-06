package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Cooldown;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract Hand getActiveHand();

    @Shadow public Input input;

    @Shadow public float lastRenderYaw;

    @Shadow public float renderYaw;

    @Shadow public float lastRenderPitch;

    @Shadow public float renderPitch;

    @Shadow public abstract float getYaw(float tickProgress);

    @Shadow @Final private Cooldown itemDropCooldown;

    @Shadow protected abstract void sendMovementPackets();

    @Shadow @Final private List<ClientPlayerTickable> tickables;

    @Shadow public abstract boolean isInSneakingPose();

    @Inject(method = "tickMovementInput", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$tickMovementInput(CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerProjectionEntity) {
            this.sidewaysSpeed = 0;
            this.forwardSpeed = 0;
            this.jumping = false;
            this.lastRenderYaw = this.renderYaw;
            this.lastRenderPitch = this.renderPitch;
            this.renderPitch += (this.getPitch() - this.renderPitch) * 0.5F;
            this.renderYaw += (this.getYaw() - this.renderYaw) * 0.5F;
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;tickLoaded()V", shift = At.Shift.AFTER), cancellable = true)
    void virtualAdditions$overrideMovementPackets(CallbackInfo ci) {
        if (this.isLoaded() && ProjectionSpyglassItem.isInUseBy(this) && MinecraftClient.getInstance().getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity) {
            this.tickLoaded();
            this.itemDropCooldown.tick();
            super.tick();
            this.sendMovementPackets();
            playerProjectionEntity.sendMovementPackets();
            for (ClientPlayerTickable clientPlayerTickable : this.tickables) {
                clientPlayerTickable.tick();
            }
            ci.cancel();
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$tickMovement(CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerProjectionEntity) {
            super.tickMovement();
            this.input.tick();
            ci.cancel();
        }
    }

    @Inject(method = "shouldStopSprinting", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$shouldStopSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }

    @Inject(method = "shouldStopSwimSprinting", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$shouldStopSwimSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isSneaking(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(false);
    }

    @Inject(method = "isCamera", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isCamera(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }
}

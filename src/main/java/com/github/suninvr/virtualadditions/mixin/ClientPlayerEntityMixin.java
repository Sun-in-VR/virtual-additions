package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.util.TickThrottler;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayer {
    public ClientPlayerEntityMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract InteractionHand getUsedItemHand();

    @Shadow public ClientInput input;

    @Shadow public abstract float getViewYRot(float tickProgress);

    @Shadow public abstract boolean isCrouching();

    @Shadow @Final private TickThrottler dropSpamThrottler;

    @Shadow protected abstract void sendPosition();

    @Shadow @Final private List<AmbientSoundHandler> ambientSoundHandlers;

    @Inject(method = "applyInput", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$tickMovementInput(CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof PlayerProjectionEntity) {
            this.xxa = 0;
            this.zza = 0;
            this.jumping = false;
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;tickClientLoadTimeout()V", shift = At.Shift.AFTER), cancellable = true)
    void virtualAdditions$overrideMovementPackets(CallbackInfo ci) {
        if (this.hasClientLoaded() && ProjectionSpyglassItem.isInUseBy(this) && Minecraft.getInstance().getCameraEntity() instanceof PlayerProjectionEntity playerProjectionEntity) {
            this.tickClientLoadTimeout();
            this.dropSpamThrottler.tick();
            super.tick();
            this.sendPosition();
            playerProjectionEntity.sendMovementPackets();
            for (AmbientSoundHandler clientPlayerTickable : this.ambientSoundHandlers) {
                clientPlayerTickable.tick();
            }
            ci.cancel();
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$tickMovement(CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof PlayerProjectionEntity) {
            super.aiStep();
            this.input.tick();
            ci.cancel();
        }
    }

    @Inject(method = "shouldStopRunSprinting", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$shouldStopSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }

    @Inject(method = "shouldStopSwimSprinting", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$shouldStopSwimSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }

    @Inject(method = "isCrouching", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isSneaking(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(false);
    }

    @Inject(method = "isControlledCamera", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isCamera(CallbackInfoReturnable<Boolean> cir) {
        if (ProjectionSpyglassItem.isInUseBy(this)) cir.setReturnValue(true);
    }
}

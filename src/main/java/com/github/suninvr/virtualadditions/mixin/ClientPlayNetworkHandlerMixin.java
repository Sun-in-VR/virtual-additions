package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.sound.FlyingLumwaspSoundInstance;
import com.github.suninvr.virtualadditions.client.sound.PlayerProjectionLoopSoundInstance;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin extends ClientCommonPacketListenerImpl {

    protected ClientPlayNetworkHandlerMixin(Minecraft client, Connection connection, CommonListenerCookie connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "postAddEntitySoundInstance", at = @At("HEAD"))
    void virtualAdditions$playLumwaspLoopSound(Entity entity, CallbackInfo ci) {
        if (entity instanceof LumwaspEntity lumwaspEntity && lumwaspEntity.isFlying()) {
            this.minecraft.getSoundManager().queueTickingSound(new FlyingLumwaspSoundInstance(lumwaspEntity));
        }

        if (entity instanceof PlayerProjectionEntity playerProjectionEntity) {
            this.minecraft.getSoundManager().queueTickingSound(new PlayerProjectionLoopSoundInstance(playerProjectionEntity));
        }
    }

    @Inject(method = "handleRotateMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V", shift = At.Shift.BEFORE), cancellable = true)
    void virtualAdditions$surpressYawSyncForPlayerProjection(ClientboundRotateHeadPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof PlayerProjectionEntity playerProjectionEntity && playerProjectionEntity.getPlayer() == this.minecraft.player) ci.cancel();
    }
}

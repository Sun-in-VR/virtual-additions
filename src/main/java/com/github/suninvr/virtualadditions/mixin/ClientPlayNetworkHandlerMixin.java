package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.sound.FlyingLumwaspSoundInstance;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.MovingMinecartSoundInstance;
import net.minecraft.client.sound.PassiveBeeSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "playSpawnSound", at = @At("HEAD"))
    void virtualAdditions$playLumwaspLoopSound(Entity entity, CallbackInfo ci) {
        if (entity instanceof LumwaspEntity lumwaspEntity && lumwaspEntity.isInAir()) {
            this.client.getSoundManager().playNextTick(new FlyingLumwaspSoundInstance(lumwaspEntity));
        }
    }
}

package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "sendToAround", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getZ()D", shift = At.Shift.AFTER))
    void virtualAdditions$sendToPlayerProjections(@Nullable PlayerEntity player, double x, double y, double z, double distance, RegistryKey<World> worldKey, Packet<?> packet, CallbackInfo ci, @Local ServerPlayerEntity serverPlayerEntity, @Local(ordinal = 4) double d, @Local(ordinal = 5) double e) {
        double f = z - serverPlayerEntity.getZ();
        if (d * d + e * e + f * f >= distance * distance && ProjectionSpyglassItem.isInUseBy(serverPlayerEntity) && ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$hasProjectionEntity()) {
            double g = x - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getX();
            double h = y - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getY();
            double i = z - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getZ();
            if (g * g + h * h + i * 8 < distance * distance) serverPlayerEntity.networkHandler.sendPacket(packet);
        }
    }
}

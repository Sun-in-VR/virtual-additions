package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerManagerMixin {
    @Inject(method = "broadcast", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getZ()D", shift = At.Shift.AFTER))
    void virtualAdditions$sendToPlayerProjections(@Nullable Player player, double x, double y, double z, double distance, ResourceKey<Level> worldKey, Packet<?> packet, CallbackInfo ci, @Local ServerPlayer serverPlayerEntity, @Local(ordinal = 4) double d, @Local(ordinal = 5) double e) {
        double f = z - serverPlayerEntity.getZ();
        if (d * d + e * e + f * f >= distance * distance && ProjectionSpyglassItem.isInUseBy(serverPlayerEntity) && ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$hasProjectionEntity()) {
            double g = x - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getX();
            double h = y - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getY();
            double i = z - ((PlayerEntityInterface)(serverPlayerEntity)).virtualAdditions$getProjectionEntity().getZ();
            if (g * g + h * h + i * 8 < distance * distance) serverPlayerEntity.connection.send(packet);
        }
    }
}

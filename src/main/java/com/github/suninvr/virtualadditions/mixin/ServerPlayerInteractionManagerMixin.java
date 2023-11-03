package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildTypes;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow @Final protected ServerPlayerEntity player;
    @Unique private static final double MAX_BREAK_SQUARED_DISTANCE_EXTENDED = 225.0;

    @Redirect(method = "processBlockBreakingAction", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double virtualAdditions$maxBreakDistanceBlock() {
        if (this.player.getMainHandStack().isIn(GildTypes.IOLITE.getTag()) || this.player.getOffHandStack().isIn(GildTypes.IOLITE.getTag())) return MAX_BREAK_SQUARED_DISTANCE_EXTENDED;
        return ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE;
    }
}

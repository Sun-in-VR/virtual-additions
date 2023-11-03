package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildTypes;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;
    @Shadow @Final public static double MAX_BREAK_SQUARED_DISTANCE;
    @Unique private static final double MAX_BREAK_SQUARED_DISTANCE_EXTENDED = 225.0;

    @Redirect(method = "onPlayerInteractBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double virtualAdditions$maxBreakDistanceBlock() {
        if (this.player.getMainHandStack().isIn(GildTypes.IOLITE.getTag()) || this.player.getOffHandStack().isIn(GildTypes.IOLITE.getTag())) return MAX_BREAK_SQUARED_DISTANCE_EXTENDED;
        return MAX_BREAK_SQUARED_DISTANCE;
    }

    @Redirect(method = "onPlayerInteractEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double virtualAdditions$maxBreakDistanceEntity() {
        if (this.player.getMainHandStack().isIn(GildTypes.IOLITE.getTag()) || this.player.getOffHandStack().isIn(GildTypes.IOLITE.getTag())) return MAX_BREAK_SQUARED_DISTANCE_EXTENDED;
        return MAX_BREAK_SQUARED_DISTANCE;
    }

    @ModifyConstant(method = "onPlayerInteractBlock", constant = @Constant(doubleValue = 64.0))
    private double virtualAdditions$extendedBlockInteractionValue(double constant) {
        return MAX_BREAK_SQUARED_DISTANCE_EXTENDED;
    }
}

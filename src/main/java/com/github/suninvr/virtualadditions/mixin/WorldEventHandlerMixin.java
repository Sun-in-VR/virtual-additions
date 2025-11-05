package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelEventHandler.class)
public class WorldEventHandlerMixin {

    @Shadow @Final private ClientLevel level;

    @Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ParticleUtils;spawnParticlesOnBlockFaces(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/util/valueproviders/IntProvider;)V", ordinal = 4, shift = At.Shift.BEFORE), cancellable = true)
    void virtualAdditions$spawnSteelScrapeParticles(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (this.level != null && this.level.getBlockState(pos).is(VABlockTags.USES_STEEL_SCRAPE_PARTICLES)) {
            ParticleUtils.spawnParticlesOnBlockFaces(this.level, pos, VAParticleTypes.SCRAPE_STEEL, UniformInt.of(3, 5));
            ci.cancel();
        }
    }
}

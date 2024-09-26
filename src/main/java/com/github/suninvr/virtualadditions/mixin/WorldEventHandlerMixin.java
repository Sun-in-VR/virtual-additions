package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.client.world.WorldEventHandler;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldEventHandler.class)
public class WorldEventHandlerMixin {
    @Shadow @Final private World world;

    @Inject(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/particle/ParticleUtil;spawnParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/util/math/intprovider/IntProvider;)V", ordinal = 4, shift = At.Shift.BEFORE), cancellable = true)
    void virtualAdditions$spawnSteelScrapeParticles(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (this.world != null && this.world.getBlockState(pos).isIn(VABlockTags.USES_STEEL_SCRAPE_PARTICLES)) {
            ParticleUtil.spawnParticle(this.world, pos, VAParticleTypes.SCRAPE_STEEL, UniformIntProvider.create(3, 5));
            ci.cancel();
        }
    }
}

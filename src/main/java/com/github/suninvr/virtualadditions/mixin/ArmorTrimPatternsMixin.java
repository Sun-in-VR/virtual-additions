package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAArmorTrimPatterns;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrimPatterns.class)
public abstract class ArmorTrimPatternsMixin {
    @Shadow
    public static void register(BootstrapContext<TrimPattern> registry, ResourceKey<TrimPattern> key) {}

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void virtualAdditions$bootstrap(BootstrapContext<TrimPattern> registry, CallbackInfo ci) {
        register(registry, VAArmorTrimPatterns.EXOSKELETON);
        register(registry, VAArmorTrimPatterns.ROBE);
    }
}

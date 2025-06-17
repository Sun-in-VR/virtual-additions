package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAArmorTrimPatterns;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.item.equipment.trim.ArmorTrimPatterns;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorTrimPatterns.class)
public abstract class ArmorTrimPatternsMixin {
    @Shadow
    public static void register(Registerable<ArmorTrimPattern> registry, RegistryKey<ArmorTrimPattern> key) {}

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void virtualAdditions$bootstrap(Registerable<ArmorTrimPattern> registry, CallbackInfo ci) {
        register(registry, VAArmorTrimPatterns.EXOSKELETON);
        register(registry, VAArmorTrimPatterns.ROBE);
    }
}

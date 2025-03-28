package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.worldgen.biome.VAOverworldBiomeCreator;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BuiltinBiomes.class)
public class BuiltinBiomesMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void virtualAdditions$bootstrapBiomes(Registerable<Biome> biomeRegisterable, CallbackInfo ci, RegistryEntryLookup<PlacedFeature> registryEntryLookup, RegistryEntryLookup<ConfiguredCarver<?>> registryEntryLookup2) {
        biomeRegisterable.register(VABiomeKeys.WASP_DEN, VAOverworldBiomeCreator.createWaspDen(registryEntryLookup, registryEntryLookup2));
        biomeRegisterable.register(VABiomeKeys.SALTY_CAVES, VAOverworldBiomeCreator.createSaltyCaves(registryEntryLookup, registryEntryLookup2));
    }
}

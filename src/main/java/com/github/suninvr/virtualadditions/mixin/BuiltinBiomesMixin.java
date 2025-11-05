package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.worldgen.biome.VAOverworldBiomeCreator;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BiomeData.class)
public class BuiltinBiomesMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void virtualAdditions$bootstrapBiomes(BootstrapContext<Biome> biomeRegisterable, CallbackInfo ci, HolderGetter<PlacedFeature> registryEntryLookup, HolderGetter<ConfiguredWorldCarver<?>> registryEntryLookup2) {
        biomeRegisterable.register(VABiomeKeys.WASP_DEN, VAOverworldBiomeCreator.createWaspDen(registryEntryLookup, registryEntryLookup2));
        biomeRegisterable.register(VABiomeKeys.SALTY_CAVES, VAOverworldBiomeCreator.createSaltyCaves(registryEntryLookup, registryEntryLookup2));
        biomeRegisterable.register(VABiomeKeys.SOUL_GROVE, VAOverworldBiomeCreator.createSoulGrove(registryEntryLookup, registryEntryLookup2));
        biomeRegisterable.register(VABiomeKeys.WITHERED_WOODS, VAOverworldBiomeCreator.createSoulGrove(registryEntryLookup, registryEntryLookup2));
    }
}

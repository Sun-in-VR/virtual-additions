package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAFeatures {
    public static final RegistryKey<PlacedFeature> ORE_IOLITE;


    static {
        ORE_IOLITE = register("ore_iolite");
    }

    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd().and( biomeSelectionContext -> !(biomeSelectionContext.getBiomeKey().equals(BiomeKeys.THE_END)) ),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_IOLITE
        );
    }

    private static RegistryKey<PlacedFeature> register(String id) {
        return PlacedFeatures.of(idOf(id).toString());
    }

}

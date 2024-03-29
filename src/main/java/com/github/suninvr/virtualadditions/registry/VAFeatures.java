package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.worldgen.feature.AerobloomTrunkPlacer;
import com.github.suninvr.virtualadditions.worldgen.feature.BalloonBulbFeature;
import com.github.suninvr.virtualadditions.worldgen.feature.LumwaspNestFeature;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAFeatures {
    public static final RegistryKey<PlacedFeature> ORE_IOLITE;
    public static final RegistryKey<ConfiguredFeature<?, ?>> AEROBLOOM_TREE;
    public static Feature<DefaultFeatureConfig> LUMWASP_NEST;
    public static Feature<DefaultFeatureConfig> BALLOON_BULB;

    static {
        ORE_IOLITE = registerPlaced("ore_iolite");
        AEROBLOOM_TREE = registerConfigured("aerobloom_tree");
    }

    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd().and( biomeSelectionContext -> !(biomeSelectionContext.getBiomeKey().equals(BiomeKeys.THE_END)) ),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_IOLITE
        );
        TrunkPlacerTypes.init();
        BALLOON_BULB = Registry.register(Registries.FEATURE, idOf("balloon_bulb"), new BalloonBulbFeature(DefaultFeatureConfig.CODEC));
        LUMWASP_NEST = Registry.register(Registries.FEATURE, idOf("lumwasp_nest"), new LumwaspNestFeature(DefaultFeatureConfig.CODEC));
    }

    private static RegistryKey<PlacedFeature> registerPlaced(String id) {
        return PlacedFeatures.of(idOf(id).toString());
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerConfigured(String id) {
        return ConfiguredFeatures.of(idOf(id).toString());
    }

    public static class TrunkPlacerTypes {
        public static final TrunkPlacerType<AerobloomTrunkPlacer> AEROBLOOM_TRUNK_PLACER = Registry.register(Registries.TRUNK_PLACER_TYPE, idOf("aerobloom_trunk_placer"), new TrunkPlacerType<>((MapCodec<AerobloomTrunkPlacer>) AerobloomTrunkPlacer.CODEC));
        public static void init(){}
    }
}

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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAFeatures {
    public static final RegistryKey<PlacedFeature> ORE_IOLITE;
    public static final RegistryKey<PlacedFeature> ORE_FLOATROCK;
    public static final RegistryKey<ConfiguredFeature<?, ?>> AEROBLOOM_TREE;
    public static Feature<DefaultFeatureConfig> LUMWASP_NEST;
    public static Feature<DefaultFeatureConfig> BALLOON_BULB;

    static {
        ORE_IOLITE = registerPlaced(idOf("ore_iolite"));
        ORE_FLOATROCK = registerPlaced(idOf("ore_floatrock"));
        AEROBLOOM_TREE = registerConfigured(idOf("aerobloom_tree"));
    }

    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd().and( biomeSelectionContext -> !(biomeSelectionContext.getBiomeKey().equals(BiomeKeys.THE_END)) ),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_IOLITE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_HILL),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_FLOATROCK
        );
        TrunkPlacerTypes.init();
        BALLOON_BULB = Registry.register(Registries.FEATURE, idOf("balloon_bulb"), new BalloonBulbFeature(DefaultFeatureConfig.CODEC));
        LUMWASP_NEST = Registry.register(Registries.FEATURE, idOf("lumwasp_nest"), new LumwaspNestFeature(DefaultFeatureConfig.CODEC));
    }

    private static RegistryKey<PlacedFeature> registerPlaced(Identifier id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerConfigured(Identifier id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
    }

    public static class TrunkPlacerTypes {
        public static final TrunkPlacerType<AerobloomTrunkPlacer> AEROBLOOM_TRUNK_PLACER = Registry.register(Registries.TRUNK_PLACER_TYPE, idOf("aerobloom_trunk_placer"), new TrunkPlacerType<>((MapCodec<AerobloomTrunkPlacer>) AerobloomTrunkPlacer.CODEC));
        public static void init(){}
    }
}

package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.worldgen.feature.*;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAFeatures {
    public static Feature<DefaultFeatureConfig> LUMWASP_NEST_FEATURE;
    public static Feature<DefaultFeatureConfig> BALLOON_BULB_FEATURE;

    public static class Placed {
        public static final RegistryKey<PlacedFeature> ORE_IOLITE;
        public static final RegistryKey<PlacedFeature> ORE_PORPHYRY;
        public static final RegistryKey<PlacedFeature> WASP_DEN_CEILING;
        public static final RegistryKey<PlacedFeature> WASP_DEN_FLOOR;
        public static final RegistryKey<PlacedFeature> GREENCAP_MUSHROOM;
        public static final RegistryKey<PlacedFeature> LUMWASP_NEST;
        public static final RegistryKey<PlacedFeature> ORE_ROCK_SALT;
        public static final RegistryKey<PlacedFeature> ORE_ROCK_SALT_OCEANS;
        public static final RegistryKey<PlacedFeature> ROCK_SALT_CEILING;
        public static final RegistryKey<PlacedFeature> ROCK_SALT_FLOOR;
        public static final RegistryKey<PlacedFeature> ORE_CALCITE;
        public static final RegistryKey<PlacedFeature> ORE_HORNFELS;
        public static final RegistryKey<PlacedFeature> ORE_BLUESCHIST;
        public static final RegistryKey<PlacedFeature> ORE_SYENITE;
        public static final RegistryKey<PlacedFeature> SOULBLOOM_TREES;

        static {
            ORE_IOLITE = registerPlaced(idOf("ore_iolite"));
            ORE_PORPHYRY = registerPlaced(idOf("ore_porphyry"));
            WASP_DEN_CEILING = registerPlaced(idOf("wasp_den_ceiling"));
            WASP_DEN_FLOOR = registerPlaced(idOf("wasp_den_floor"));
            GREENCAP_MUSHROOM = registerPlaced(idOf("greencap_mushroom"));
            LUMWASP_NEST = registerPlaced(idOf("lumwasp_nest"));
            ORE_ROCK_SALT = registerPlaced(idOf("ore_rock_salt"));
            ORE_ROCK_SALT_OCEANS = registerPlaced(idOf("ore_rock_salt_oceans"));
            ROCK_SALT_CEILING = registerPlaced(idOf("rock_salt_ceiling"));
            ROCK_SALT_FLOOR = registerPlaced(idOf("rock_salt_floor"));
            ORE_CALCITE = registerPlaced(idOf("ore_calcite"));
            ORE_HORNFELS = registerPlaced(idOf("ore_hornfels"));
            ORE_BLUESCHIST = registerPlaced(idOf("ore_blueschist"));
            ORE_SYENITE = registerPlaced(idOf("ore_syenite"));
            SOULBLOOM_TREES = registerPlaced(idOf("soulbloom_trees"));
        }

        //public void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        //    PlacedFeatures.register(featureRegisterable, );
        //}
    }

    public static class Configured {
        public static final RegistryKey<ConfiguredFeature<?, ?>> SOULBLOOM_TREE;
        public static final RegistryKey<ConfiguredFeature<?, ?>> WITHERED_TREE;
        public static final RegistryKey<ConfiguredFeature<?, ?>> NECROTIC_ROOTS_BONEMEAL;

        static {
            SOULBLOOM_TREE = registerConfigured(idOf("soulbloom_tree"));
            WITHERED_TREE = registerConfigured(idOf("withered_tree"));
            NECROTIC_ROOTS_BONEMEAL = registerConfigured(idOf("necrotic_roots_bonemeal"));
        }
    }

    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd().and( biomeSelectionContext -> !(biomeSelectionContext.getBiomeKey().equals(BiomeKeys.THE_END)) ),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_IOLITE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_PORPHYRY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_ROCK_SALT
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_HORNFELS
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_BLUESCHIST
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_SYENITE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OCEAN),
                GenerationStep.Feature.UNDERGROUND_ORES,
                Placed.ORE_ROCK_SALT_OCEANS
        );
        TrunkPlacerTypes.init();
        BALLOON_BULB_FEATURE = Registry.register(Registries.FEATURE, idOf("balloon_bulb"), new BalloonBulbFeature(DefaultFeatureConfig.CODEC));
        LUMWASP_NEST_FEATURE = Registry.register(Registries.FEATURE, idOf("lumwasp_nest"), new LumwaspNestFeature(DefaultFeatureConfig.CODEC));
    }

    private static RegistryKey<PlacedFeature> registerPlaced(Identifier id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerConfigured(Identifier id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
    }

    public static class TrunkPlacerTypes {
        public static final TrunkPlacerType<SoulbloomTrunkPlacer> SOULBLOOM_TRUNK_PLACER = Registry.register(Registries.TRUNK_PLACER_TYPE, idOf("soulbloom_trunk_placer"), new TrunkPlacerType<>(SoulbloomTrunkPlacer.CODEC));
        public static final TrunkPlacerType<WitheredTrunkPlacer> WITHERED_TRUNK_PLACER = Registry.register(Registries.TRUNK_PLACER_TYPE, idOf("withered_trunk_placer"), new TrunkPlacerType<>(WitheredTrunkPlacer.CODEC));
        public static void init(){}
    }

    public static class FoliagePlacerTypes {
        //public static final FoliagePlacerType<WitheredFoliagePlacer> WITHERED_TRUNK_PLACER = Registry.register(Registries.FOLIAGE_PLACER_TYPE, idOf("withered_foliage_placer"), new FoliagePlacerType<>(WitheredFoliagePlacer.CODEC));
        public static void init(){}
    }
}

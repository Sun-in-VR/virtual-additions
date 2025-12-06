package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.worldgen.feature.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAFeatures {
    public static Feature<NoneFeatureConfiguration> LUMWASP_NEST_FEATURE;
    public static Feature<NoneFeatureConfiguration> BALLOON_BULB_FEATURE;

    public static class Placed {
        public static final ResourceKey<PlacedFeature> ORE_IOLITE;
        public static final ResourceKey<PlacedFeature> ORE_PORPHYRY;
        public static final ResourceKey<PlacedFeature> WASP_DEN_CEILING;
        public static final ResourceKey<PlacedFeature> WASP_DEN_FLOOR;
        public static final ResourceKey<PlacedFeature> GREENCAP_MUSHROOM;
        public static final ResourceKey<PlacedFeature> LUMWASP_NEST;
        public static final ResourceKey<PlacedFeature> ORE_ROCK_SALT;
        public static final ResourceKey<PlacedFeature> ORE_ROCK_SALT_OCEANS;
        public static final ResourceKey<PlacedFeature> ROCK_SALT_CEILING;
        public static final ResourceKey<PlacedFeature> ROCK_SALT_FLOOR;
        public static final ResourceKey<PlacedFeature> ORE_CALCITE;
        public static final ResourceKey<PlacedFeature> ORE_HORNFELS;
        public static final ResourceKey<PlacedFeature> ORE_BLUESCHIST;
        public static final ResourceKey<PlacedFeature> ORE_SYENITE;
        public static final ResourceKey<PlacedFeature> SOULBLOOM_TREES;
        public static final ResourceKey<PlacedFeature> BONE_LITTER_SOUL_SAND_VALLEY;
        public static final ResourceKey<PlacedFeature> SPRING_LOTUS_SWAMP;
        public static final ResourceKey<PlacedFeature> ZEBRANO_TREE;

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
            BONE_LITTER_SOUL_SAND_VALLEY = registerPlaced(idOf("bone_litter_soul_sand_valley"));
            SPRING_LOTUS_SWAMP = registerPlaced(idOf("spring_lotus_swamp"));
            ZEBRANO_TREE = registerPlaced(idOf("zebrano_tree"));
        }
    }

    public static class Configured {
        public static final ResourceKey<ConfiguredFeature<?, ?>> SOULBLOOM_TREE;
        public static final ResourceKey<ConfiguredFeature<?, ?>> WITHERED_TREE;
        public static final ResourceKey<ConfiguredFeature<?, ?>> ZEBRANO_TREE;
        public static final ResourceKey<ConfiguredFeature<?, ?>> NECROTIC_ROOTS_BONEMEAL;

        static {
            SOULBLOOM_TREE = registerConfigured(idOf("soulbloom_tree"));
            WITHERED_TREE = registerConfigured(idOf("withered_tree"));
            ZEBRANO_TREE = registerConfigured(idOf("zebrano_tree"));
            NECROTIC_ROOTS_BONEMEAL = registerConfigured(idOf("necrotic_roots_bonemeal"));
        }
    }

    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd().and( biomeSelectionContext -> !(biomeSelectionContext.getBiomeKey().equals(Biomes.THE_END)) ),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_IOLITE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_PORPHYRY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_ROCK_SALT
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_HORNFELS
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_BLUESCHIST
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_SYENITE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OCEAN),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Placed.ORE_ROCK_SALT_OCEANS
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                Placed.BONE_LITTER_SOUL_SAND_VALLEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                Placed.SPRING_LOTUS_SWAMP
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                Placed.ZEBRANO_TREE
        );
        TrunkPlacerTypes.init();
        BALLOON_BULB_FEATURE = Registry.register(BuiltInRegistries.FEATURE, idOf("balloon_bulb"), new BalloonBulbFeature(NoneFeatureConfiguration.CODEC));
        LUMWASP_NEST_FEATURE = Registry.register(BuiltInRegistries.FEATURE, idOf("lumwasp_nest"), new LumwaspNestFeature(NoneFeatureConfiguration.CODEC));
    }

    private static ResourceKey<PlacedFeature> registerPlaced(Identifier id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerConfigured(Identifier id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id);
    }

    public static class TrunkPlacerTypes {
        public static final TrunkPlacerType<SoulbloomTrunkPlacer> SOULBLOOM_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, idOf("soulbloom_trunk_placer"), new TrunkPlacerType<>(SoulbloomTrunkPlacer.CODEC));
        public static final TrunkPlacerType<WitheredTrunkPlacer> WITHERED_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, idOf("withered_trunk_placer"), new TrunkPlacerType<>(WitheredTrunkPlacer.CODEC));
        public static final TrunkPlacerType<DivergingTrunkPlacer> DIVERGING_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, idOf("diverging_trunk_placer"), new TrunkPlacerType<>(DivergingTrunkPlacer.CODEC));
        public static void init(){}
    }

    public static class FoliagePlacerTypes {
        public static void init(){}
    }
}

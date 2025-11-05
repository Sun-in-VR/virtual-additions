package com.github.suninvr.virtualadditions.worldgen.biome;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class VAOverworldBiomeCreator {

    public static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    public static Biome createSoulGrove(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(4159204)
                .grassColorOverride(6801570)
                .foliageColorOverride(6801570);

        MobSpawnSettings.Builder spawners = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.caveSpawns(spawners);
        BiomeDefaultFeatures.monsters(spawners, 20, 5, 0, 100, false);
        spawners.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 1, 1));

        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        addBasicFeatures(generation);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addDefaultOres(generation, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addPlainVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VAFeatures.Placed.SOULBLOOM_TREES);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(effects.build())
                .mobSpawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome createSaltyCaves(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(16502975);

        MobSpawnSettings.Builder spawners = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.caveSpawns(spawners);
        BiomeDefaultFeatures.monsters(spawners, 20, 5, 0, 100, false);
        spawners.addSpawn(MobCategory.MONSTER, 75, new MobSpawnSettings.SpawnerData(VAEntityType.SALINE, 4, 4));

        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        addBasicFeatures(generation);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addDefaultOres(generation, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addPlainVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VAFeatures.Placed.ORE_ROCK_SALT_OCEANS);
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VAFeatures.Placed.ORE_CALCITE);
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VAFeatures.Placed.ROCK_SALT_CEILING);
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VAFeatures.Placed.ROCK_SALT_FLOOR);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(effects.build())
                .mobSpawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome createWaspDen(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(4159204);

        MobSpawnSettings.Builder spawners = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.caveSpawns(spawners);
        spawners.addSpawn(MobCategory.MONSTER, 500, new MobSpawnSettings.SpawnerData(VAEntityType.LUMWASP, 2, 3));

        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        addBasicFeaturesWithoutLavaSprings(generation);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addDefaultOres(generation, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addPlainVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VAFeatures.Placed.WASP_DEN_CEILING);
            generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VAFeatures.Placed.WASP_DEN_FLOOR);
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VAFeatures.Placed.GREENCAP_MUSHROOM);
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VAFeatures.Placed.LUMWASP_NEST);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(effects.build())
                .mobSpawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    private static void addBasicFeatures(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    private static void addBasicFeaturesWithoutLavaSprings(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }
}

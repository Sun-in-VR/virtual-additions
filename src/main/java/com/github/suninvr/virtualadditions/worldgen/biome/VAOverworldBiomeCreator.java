package com.github.suninvr.virtualadditions.worldgen.biome;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class VAOverworldBiomeCreator {

    public static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    public static Biome createSoulGrove(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        BiomeEffects.Builder effects = new BiomeEffects.Builder()
                .skyColor(8103167)
                .fogColor(12638463)
                .waterColor(4159204)
                .waterFogColor(329011)
                .grassColor(6801570)
                .foliageColor(6801570)
                .moodSound(BiomeMoodSound.CAVE)
                .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES));

        SpawnSettings.Builder spawners = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addCaveMobs(spawners);
        DefaultBiomeFeatures.addMonsters(spawners, 20, 5, 100, false);
        spawners.spawn(SpawnGroup.CREATURE, 1, new SpawnSettings.SpawnEntry(EntityType.WOLF, 1, 1));

        GenerationSettings.LookupBackedBuilder generation = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addBasicFeatures(generation);
        DefaultBiomeFeatures.addPlainsTallGrass(generation);
        DefaultBiomeFeatures.addDefaultOres(generation, true);
        DefaultBiomeFeatures.addDefaultDisks(generation);
        DefaultBiomeFeatures.addPlainsFeatures(generation);
        DefaultBiomeFeatures.addDefaultMushrooms(generation);
        DefaultBiomeFeatures.addDefaultVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, VAFeatures.Placed.AEROBLOOM_TREES_IN_HILLS);
        }

        return new Biome.Builder()
                .precipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .effects(effects.build())
                .spawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome createSaltyCaves(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        BiomeEffects.Builder effects = new BiomeEffects.Builder()
                .skyColor(8103167)
                .fogColor(12638463)
                .waterColor(16502975)
                .waterFogColor(16563366)
                .moodSound(BiomeMoodSound.CAVE)
                .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES));

        SpawnSettings.Builder spawners = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addCaveMobs(spawners);
        DefaultBiomeFeatures.addMonsters(spawners, 20, 5, 100, false);
        spawners.spawn(SpawnGroup.MONSTER, 75, new SpawnSettings.SpawnEntry(VAEntityType.SALINE, 4, 4));

        GenerationSettings.LookupBackedBuilder generation = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addBasicFeatures(generation);
        DefaultBiomeFeatures.addPlainsTallGrass(generation);
        DefaultBiomeFeatures.addDefaultOres(generation, true);
        DefaultBiomeFeatures.addDefaultDisks(generation);
        DefaultBiomeFeatures.addPlainsFeatures(generation);
        DefaultBiomeFeatures.addDefaultMushrooms(generation);
        DefaultBiomeFeatures.addDefaultVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, VAFeatures.Placed.ORE_ROCK_SALT_OCEANS);
            generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, VAFeatures.Placed.ORE_CALCITE);
            generation.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, VAFeatures.Placed.ROCK_SALT_CEILING);
            generation.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, VAFeatures.Placed.ROCK_SALT_FLOOR);
        }

        return new Biome.Builder()
                .precipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .effects(effects.build())
                .spawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome createWaspDen(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        BiomeEffects.Builder effects = new BiomeEffects.Builder()
                .skyColor(8103167)
                .fogColor(12638463)
                .waterColor(4159204)
                .waterFogColor(329011)
                .moodSound(BiomeMoodSound.CAVE)
                .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES));

        SpawnSettings.Builder spawners = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addCaveMobs(spawners);
        spawners.spawn(SpawnGroup.MONSTER, 500, new SpawnSettings.SpawnEntry(VAEntityType.LUMWASP, 2, 3));

        GenerationSettings.LookupBackedBuilder generation = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addBasicFeaturesWithoutLavaSprings(generation);
        DefaultBiomeFeatures.addPlainsTallGrass(generation);
        DefaultBiomeFeatures.addDefaultOres(generation, true);
        DefaultBiomeFeatures.addDefaultDisks(generation);
        DefaultBiomeFeatures.addPlainsFeatures(generation);
        DefaultBiomeFeatures.addDefaultMushrooms(generation);
        DefaultBiomeFeatures.addDefaultVegetation(generation, false);

        if (!VirtualAdditions.isDataGenerationActive) {
            generation.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, VAFeatures.Placed.WASP_DEN_CEILING);
            generation.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, VAFeatures.Placed.WASP_DEN_FLOOR);
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, VAFeatures.Placed.GREENCAP_MUSHROOM);
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, VAFeatures.Placed.LUMWASP_NEST);
        }

        return new Biome.Builder()
                .precipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .effects(effects.build())
                .spawnSettings(spawners.build())
                .generationSettings(generation.build())
                .build();
    }

    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    private static void addBasicFeaturesWithoutLavaSprings(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        generationSettings.feature(GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_WATER);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }
}

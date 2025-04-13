package com.github.suninvr.virtualadditions.worldgen.biome;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class VANetherBiomeCreator {

    public static Biome createWitheredWoods(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
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
            generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, VAFeatures.Placed.SOULBLOOM_TREES);
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

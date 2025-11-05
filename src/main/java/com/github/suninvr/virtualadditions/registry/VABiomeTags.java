package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABiomeTags {
    public static final TagKey<Biome> SPAWNS_ENCHANTED_VARIANT_FARM_ANIMALS = register("spawns_enchanted_variant_farm_animals");

    private static TagKey<Biome> register(String id) {
        return TagKey.create(Registries.BIOME, idOf(id));
    }

    public static void init(){}
}

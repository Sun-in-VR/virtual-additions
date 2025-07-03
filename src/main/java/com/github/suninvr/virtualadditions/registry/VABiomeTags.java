package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABiomeTags {
    public static final TagKey<Biome> SPAWNS_ENCHANTED_VARIANT_FARM_ANIMALS = register("spawns_enchanted_variant_farm_animals");

    private static TagKey<Biome> register(String id) {
        return TagKey.of(RegistryKeys.BIOME, idOf(id));
    }

    public static void init(){}
}

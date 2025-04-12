package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABiomeKeys {
    public static final RegistryKey<Biome> SALTY_CAVES = keyOf("salty_caves");
    public static final RegistryKey<Biome> WASP_DEN = keyOf("wasp_den");
    public static final RegistryKey<Biome> SOUL_GROVE = keyOf("soul_grove");

    private static RegistryKey<Biome> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, idOf(id));
    }

    public static void init(){}
}

package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.impl.biome.NetherBiomeData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABiomeKeys {
    public static final ResourceKey<Biome> SALTY_CAVES = keyOf("salty_caves");
    public static final ResourceKey<Biome> WASP_DEN = keyOf("wasp_den");
    public static final ResourceKey<Biome> SOUL_GROVE = keyOf("soul_grove");
    public static final ResourceKey<Biome> WITHERED_WOODS = keyOf("withered_woods");

    private static ResourceKey<Biome> keyOf(String id) {
        return ResourceKey.create(Registries.BIOME, idOf(id));
    }

    public static void init(){
        NetherBiomeData.addNetherBiome(WITHERED_WOODS, new Climate.ParameterPoint(
                Climate.Parameter.point(0.3F),
                Climate.Parameter.point(-0.5F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                0L
        ));
    }
}

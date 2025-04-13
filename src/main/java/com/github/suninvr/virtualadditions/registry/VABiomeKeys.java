package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.impl.biome.NetherBiomeData;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABiomeKeys {
    public static final RegistryKey<Biome> SALTY_CAVES = keyOf("salty_caves");
    public static final RegistryKey<Biome> WASP_DEN = keyOf("wasp_den");
    public static final RegistryKey<Biome> SOUL_GROVE = keyOf("soul_grove");
    public static final RegistryKey<Biome> WITHERED_WOODS = keyOf("withered_woods");

    private static RegistryKey<Biome> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, idOf(id));
    }

    public static void init(){
        NetherBiomeData.addNetherBiome(WITHERED_WOODS, new MultiNoiseUtil.NoiseHypercube(
                MultiNoiseUtil.ParameterRange.of(0.3F),
                MultiNoiseUtil.ParameterRange.of(-0.5F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                0L
        ));
    }
}

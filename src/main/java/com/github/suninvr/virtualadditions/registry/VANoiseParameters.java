package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VANoiseParameters {
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> ANDESITE = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, idOf("andesite"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> DIORITE = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, idOf("diorite"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> GRANITE = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, idOf("granite"));
}

package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VANoiseParameters {
    public static final ResourceKey<NormalNoise.NoiseParameters> ANDESITE = ResourceKey.create(Registries.NOISE, idOf("andesite"));
    public static final ResourceKey<NormalNoise.NoiseParameters> DIORITE = ResourceKey.create(Registries.NOISE, idOf("diorite"));
    public static final ResourceKey<NormalNoise.NoiseParameters> GRANITE = ResourceKey.create(Registries.NOISE, idOf("granite"));
}

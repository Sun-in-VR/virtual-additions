package com.github.suninvr.virtualadditions.registry;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import com.github.suninvr.virtualadditions.worldgen.feature.LargeCrystalFeature;
import com.github.suninvr.virtualadditions.worldgen.feature.LargeCrystalFeatureConfig;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAFeatures {
    private static final Feature<LargeCrystalFeatureConfig> LARGE_CRYSTAL = new LargeCrystalFeature(LargeCrystalFeatureConfig.CODEC);

    public static void init() {
        Registry.register(Registry.FEATURE, idOf("large_crystal"), LARGE_CRYSTAL);
    }
}

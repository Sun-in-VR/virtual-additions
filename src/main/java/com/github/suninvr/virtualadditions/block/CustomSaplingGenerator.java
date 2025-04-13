package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class CustomSaplingGenerator {
    public static final SaplingGenerator SOULBLOOM = new SaplingGenerator("soulbloom", Optional.empty(), Optional.of(VAFeatures.Configured.SOULBLOOM_TREE), Optional.empty());
    public static final SaplingGenerator WITHERED = new SaplingGenerator("withered", Optional.empty(), Optional.of(VAFeatures.Configured.WITHERED_TREE), Optional.empty());
}

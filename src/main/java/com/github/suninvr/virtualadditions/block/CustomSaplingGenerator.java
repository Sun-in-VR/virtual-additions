package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class CustomSaplingGenerator {
    public static final TreeGrower SOULBLOOM = new TreeGrower("soulbloom", Optional.empty(), Optional.of(VAFeatures.Configured.SOULBLOOM_TREE), Optional.empty());
    public static final TreeGrower WITHERED = new TreeGrower("withered", Optional.empty(), Optional.of(VAFeatures.Configured.WITHERED_TREE), Optional.empty());
}

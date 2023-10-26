package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class CustomSaplingGenerator {
    public static final SaplingGenerator AEROBLOOM = new SaplingGenerator("aerobloom", Optional.empty(), Optional.of(VAFeatures.AEROBLOOM_TREE), Optional.empty());
}

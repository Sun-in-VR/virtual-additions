package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAFluidTags {
    public static final TagKey<Fluid> ACID = register("acid");

    public static void init(){}

    private static TagKey<Fluid> register(String id) {
        return TagKey.of(Registry.FLUID_KEY, idOf(id));
    }
}
